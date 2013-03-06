package com.misys.responsebuilder;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLFileHandler 
{
	public static String replaceNodeValues(String filename, List<XMLObject> XMLContent) throws ParserConfigurationException
	{	System.out.println("Filename" + filename);
		File xmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		try {
			doc = dBuilder.parse(xmlFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		Node root = doc.getFirstChild();
		String output ="";
		for (XMLObject xmlObject : XMLContent) 
		{
			NodeList nodeList = doc.getElementsByTagName(xmlObject.xmltag);	
			System.out.println(nodeList.getLength());
			for(int i=0;i<nodeList.getLength();i++)
			{
				Node valueNode = nodeList.item(i);
				if(valueNode.getFirstChild() == null )
				{
					Node node = doc.createTextNode(xmlObject.xmlValue);
					valueNode.appendChild(node);
				}
				else
				{
					nodeList.item(i).getFirstChild().setNodeValue(xmlObject.xmlValue);	
				}
			}
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try 
		{
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
		
	}

		
}

