package com.misys.responsebuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 * @author Brunall
 * This class performs a lookup for incoming excel sheets in the polled directory
 */
public class ExcelLookup 
{
	public static String polledDirectory = "D:\\Misys\\UOBTestEnvi\\Testing System For Portal\\TestCase";



	public ExcelLookup() 
	{
		super();
	}
	public static boolean isExcelPresent()
	{
		File dir = new File(polledDirectory);
		File[] listOfFiles = dir.listFiles(); 
		return  (listOfFiles !=null) ? true :false; 
	}

	
	public static List<MessageInformation> FileIterator() throws IOException
	{	System.out.println("FileIterator1");
		File dir = new File(polledDirectory);
		File[] listOfFiles = dir.listFiles(); 
		List<MessageInformation> msgInformation = new ArrayList<MessageInformation>();
		for (File file : listOfFiles)
		{
			 msgInformation.add(fetchFileData(file.getAbsolutePath()));
		}
		return msgInformation;
	}
	  public static MessageInformation fetchFileData(String inputFile) throws IOException  
	  {		System.out.println("fetchFileData1");
		    File inputWorkbook = new File(inputFile);
		    // create message information 
		    MessageInformation msgInfo = new MessageInformation();
		    Workbook w;
		    try {
		      w = Workbook.getWorkbook(inputWorkbook);
		      // Get the first sheet
		      Sheet sheet = w.getSheet(0);
		      // iterate only 1 time because message information avalible in first row
		      for (int j = 1; j < 2; j++) {
		        for (int i = 0; i < sheet.getColumns(); i++) {
		          jxl.Cell cell = sheet.getCell(i,j);
		          String cellValue = cell.getContents();
		          // switch: refer Xl
		          // 0 - interface
		          // 1 - from queue name
		          // 2 - to queue name
		          // 3 - file location
		          switch(i)
		          {
		             case 0 : msgInfo.setInterfaceName(cellValue);
		             System.out.println("Interface name *****************"+cellValue);
		             		  break;
		             case 1 : msgInfo.setFromQueue(cellValue);
		             System.out.println("From Queue name *****************"+cellValue);
		             		  break;
		             case 2 :  msgInfo.setToQueue(cellValue);
		             System.out.println("To Queue name *****************"+cellValue);
		             		  break;
		             case 3 : msgInfo.setXmlDirectory(cellValue);
		             System.out.println("XML File Location *****************"+cellValue);
		             		  break;
		          }
		        }
		      }
		      for(int i = 1 ; i < sheet.getRows(); i++)
		      {
		    	  XMLObject xmlobject = new XMLObject();
		    	  xmlobject.xmltag = sheet.getCell(4,i).getContents();
		          xmlobject.xmlValue = sheet.getCell(5,i).getContents();  
		          msgInfo.getXMLContent().add(xmlobject);
		      }
		      
		    }
		    catch (BiffException e) {
		      e.printStackTrace();
		    }
		    return msgInfo;
	  }
}
