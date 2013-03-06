package com.misys.listener.daemon;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.misys.responsebuilder.ExcelLookup;
import com.misys.responsebuilder.MessageInformation;
import com.misys.responsebuilder.XMLFileHandler;
import com.misys.responsebuilder.XMLObject;

/**
 * Class responsible to read message from the queue, build the response and send
 * the response to respective Queue
 * 
 * @author pavkumar
 * 
 */
public class MessageSender extends Thread {

	public static final String ENV_CONTEXT = "java:comp/env";

	public static final String CONNECTION_FACTORY = "jms/ConnectionFactory";

	private static Log log = LogFactory.getLog(MessageSender.class);

	public MessageSender() {
		setDaemon(true);
	}

	public void sendMessage() {

		InitialContext initCtx = null;
		Context envContext = null;
		Message message;
		Message recieveRequestMessage;
		try {
			/*if (ExcelLookup.isExcelPresent()) {*/
				
				List<MessageInformation> XMLdata = ExcelLookup.FileIterator();

				for (MessageInformation msgInfo : XMLdata) {
					// get the response message
					String responseMessage = XMLFileHandler.replaceNodeValues(
							msgInfo.getXmlDirectory(), msgInfo.getXMLContent());
					// create the initial context
					initCtx = new InitialContext();
					// get the current context
					envContext = (Context) initCtx.lookup(ENV_CONTEXT);
					// prepare the connection factory
					ConnectionFactory connectionFactory = null;
					// get the connection factory
					connectionFactory = (ConnectionFactory) envContext
							.lookup(CONNECTION_FACTORY);
					recieveRequestMessage = recieveMessage(msgInfo
							.getFromQueue());
					// prepare to connect to queue
					Connection connection = null;
					connection = (Connection) connectionFactory
							.createConnection();
					Session session = null;
					session = connection.createSession(false,
							Session.AUTO_ACKNOWLEDGE);
					Destination destination = null;
					// set the sender queue name
					destination = session.createQueue(msgInfo.getToQueue());
					// prepare the producer to send a message to queue
					MessageProducer producer = null;
					producer = session.createProducer(destination);
					// get the message
					// set the same message id as the request
					message = session.createTextMessage(responseMessage);
					if (recieveRequestMessage != null) {
						System.out
								.println("Recieved Message From the Sender qUEUE"
										+ recieveRequestMessage
												.getJMSMessageID());
						message.setJMSCorrelationID(getJMSCorrelationID(recieveRequestMessage));
						producer.send(message);
						System.out
								.println("Response Message" + responseMessage);
					}
					// close connections
					producer.close();
					session.close();
					connection.close();

				}
			//}
		} catch (JMSException e) {

			System.out.print("Exception " + e);
		} catch (NamingException e) {
			System.out.print("Exception " + e);
		} catch (IOException e) {
			System.out.print("Exception " + e);
		} catch (ParserConfigurationException e) {
			System.out.print("Exception " + e);
		}

	}

	private String getJMSCorrelationID(Message message) throws JMSException {
		// DB Correlation and status update
		String jmsMessageID = message.getJMSMessageID();
		// Trim the 'ID:' from the jmsMessageID string
		jmsMessageID = "ID:" + jmsMessageID.substring(3, jmsMessageID.length());

		System.out.println("JMS MEssages id" + jmsMessageID);
		return jmsMessageID;
	}

	/**
	 * Recieves the message from the given queue
	 * 
	 * @param receiveRequestMessageQueueName
	 * @param envContext
	 * @param connectionFactory
	 * @return
	 * @throws JMSException
	 * @throws NamingException
	 */

	private Message recieveMessage(String receiveRequestMessageQueueName)
			throws JMSException, NamingException {
		MessageConsumer consumer = null;
		Connection connection = null;
		InitialContext initCtx1 = null;
		Context envContext = null;

		initCtx1 = new InitialContext();
		// get the current context
		envContext = (Context) initCtx1.lookup(ENV_CONTEXT);
		Queue queue = (Queue) envContext.lookup(receiveRequestMessageQueueName);
		// prepare the connection factory
		ConnectionFactory connectionFactory = null;
		// get the connection factory
		connectionFactory = (ConnectionFactory) envContext
				.lookup(CONNECTION_FACTORY);
		connection = (Connection) connectionFactory.createConnection();
		Session session = null;
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		consumer = session.createConsumer(queue);

		Message message = consumer.receive(2000);

		consumer.close();
		session.close();
		connection.close();

		return message;
	}

	@Override
	public void run() {
		sendMessage();
	}

}
