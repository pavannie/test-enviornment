package com.misys.responsebuilder;

import java.util.ArrayList;
import java.util.List;

public class MessageInformation 
{
	
	private String interfaceName;
	private String fromQueue;
	private String toQueue;
	private String xmlDirectory;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getFromQueue() {
		return fromQueue;
	}

	public void setFromQueue(String fromQueue) {
		this.fromQueue = fromQueue;
	}

	public String getToQueue() {
		return toQueue;
	}

	public void setToQueue(String toQueue) {
		this.toQueue = toQueue;
	}

	public String getXmlDirectory() {
		return xmlDirectory;
	}

	public void setXmlDirectory(String xmlDirectory) {
		this.xmlDirectory = xmlDirectory;
	}

	public List<XMLObject> getXMLContent() {
		return XMLContent;
	}

	public void setXMLContent(List<XMLObject> xMLContent) {
		XMLContent = xMLContent;
	}

	public List<XMLObject> XMLContent = new ArrayList<XMLObject>();

	public MessageInformation() {
		super();
	}
}
