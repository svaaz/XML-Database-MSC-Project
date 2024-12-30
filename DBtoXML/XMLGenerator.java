/*
 * Created on Jan 25, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author srini
 *
 * Efficient Method for converting Database table into XML DOM then 
 * to a XML File
 * 
 * It converts large tables Efficiently
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLGenerator
{
	static String nameofFile="Noname";	
	
public static Document createXML(ResultSet rs)throws ParserConfigurationException, SQLException
{
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder        = factory.newDocumentBuilder();
Document doc                   = builder.newDocument();

Element results = doc.createElement("Results");
doc.appendChild(results);

ResultSetMetaData rsmd = rs.getMetaData();
int colCount           = rsmd.getColumnCount();

while (rs.next())
{
   Element row = doc.createElement("Row");
   results.appendChild(row);

   for (int i = 1; i <= colCount; i++)
   {
      String columnName = rsmd.getColumnName(i);
      Object value      = rs.getObject(i);
      System.out.println(value);
      if(value==null)
	  value="unspecified";
      Element node      = doc.createElement(columnName);
      node.appendChild(doc.createTextNode(value.toString()));
      row.appendChild(node);
      
   }
}

return doc;
}

public Document getxml(String table)
{
	if(table==null)
		System.exit(1);
   
	Document doc = null;

   try
   {
   	//  Check for  the Driver
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	
	// Data source
	  Connection conn = DriverManager.getConnection("jdbc:odbc:mydsn");
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * from "+table);
      ResultSetMetaData rsm=rs.getMetaData();
      nameofFile=rsm.getTableName(1);
      doc = createXML(rs);

      rs.close();
      stmt.close();
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }

   return doc;
}
//method to convert the DOM tree to String 
public static String XMLDOM2String(Document doc) throws TransformerException
{
	
	DOMSource ds=new DOMSource(doc);
	StringWriter w=new StringWriter();
	StreamResult r=new StreamResult(w);
	TransformerFactory tf= TransformerFactory.newInstance();
	Transformer t=tf.newTransformer();
	t.transform(ds,r);
	System.out.println(w.toString());	
	return w.toString();
	
	
}

//Main method
public  void Convert(String table) throws Exception
{
	XMLGenerator xmlgen=new XMLGenerator();
	 System.out.println("Table Name:"+table);
    Document doc=xmlgen.getxml(table);
   
    //String nameoffile=doc.getFirstChild().getLocalName();
    System.out.println("Document Created");
   
   
      //writing to an XML FILE
     File XMLFile=new File("e:\\xml\\"+nameofFile+".xml");
     BufferedWriter out = new BufferedWriter(new FileWriter(XMLFile));
     out.write(XMLDOM2String(doc));
	 out.newLine();
	 out.flush();
	 Runtime rt=Runtime.getRuntime();
	 rt.exec("C:\\Program Files\\Mozilla Firefox\\firefox.exe "+XMLFile.toString());
	 
      
}

}
