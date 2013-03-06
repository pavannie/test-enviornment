package com.misys.publisher;

import java.io.IOException;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.misys.listener.daemon.MessageSender;
/**
 * Servlet implementation class Publisher
 */
public class Publisher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Log log = LogFactory.getLog(MessageSender.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Publisher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InitialContext initCtx;
		try {/*
			initCtx = new InitialContext();
			Context envContext = (Context) initCtx.lookup("java:comp/env");
			ConnectionFactory connectionFactory = (ConnectionFactory) envContext.lookup("jms/ConnectionFactory");
			javax.jms.Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("jms/topic/MyTopic");
			MessageProducer producer = session.createProducer(destination);
			TextMessage msg=session.createTextMessage();
			msg.setText("Message sent");
			producer.send(msg);
			
		*/
			
			log.info("Here we goooooo ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
