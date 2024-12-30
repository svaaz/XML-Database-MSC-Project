

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.SAXSource;

import java.io.*;
import java.net.URL;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class TextConvertor implements XMLReader
{					
	private ContentHandler contentHandler = null;
	AttributesImpl attribs = new AttributesImpl();
	String namespaceURI = "";

	private void parse(Properties p) throws SAXException
	{
	    contentHandler.startDocument();
	    contentHandler.startElement(namespaceURI, "Properties", "Properties", attribs);
	    
	    Enumeration e = p.propertyNames();
        
        while (e.hasMoreElements())
        {
            String key = (String)e.nextElement();
            String value = (String)p.getProperty(key);
           
 		    contentHandler.startElement(namespaceURI, key, key, attribs);  	    
 		    contentHandler.characters(value.toCharArray(), 0, value.length()); 
 		    contentHandler.endElement(namespaceURI, key, key);                       
        } 
	    
		contentHandler.endElement(namespaceURI, "Properties", "Properties");
		contentHandler.endDocument();
	}
	
	
	public void parse(InputSource source) throws IOException, SAXException
	{
	    InputStream is = source.getByteStream(); 
	    Properties p = new Properties();
	    p.load(is);
	    parse(p);
	}
	
	public void parse(String uri) 
	{

	}
	public void setContentHandler(ContentHandler handler)
	{
		contentHandler = handler;
	}

	public ContentHandler getContentHandler()
	{
		return contentHandler;
	}
	
	public boolean getFeature(String s)		
	{
        return false;
	}

	public void setFeature(String s, boolean b)
	{
	}

	public Object getProperty(String s)
	{
        return null;
	}

	public void setProperty(String s, Object o)
	{
	}

	public void setEntityResolver(EntityResolver e)
	{
	}

	public EntityResolver getEntityResolver()
	{
		return null;
	}

	public void setDTDHandler(DTDHandler d)
	{
	}

	public DTDHandler getDTDHandler()
	{
		return null;
	}

	public void setErrorHandler(ErrorHandler handler)
	{
	}

	public ErrorHandler getErrorHandler()
	{
		return null;
	}	
	
	

	public static void main(String[] args) throws Exception
	{
        // construct SAXSource with custom XMLReader
        InputStream props = ClassLoader.getSystemResourceAsStream("my.properties");
        InputSource inputSource = new InputSource(props);
        XMLReader parser	    = new TextConvertor();
        SAXSource saxSource		= new SAXSource(parser, inputSource);

        // construct a transformer using the generic stylesheet
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslSource     = new StreamSource("echo.xsl");
        Transformer transformer    = factory.newTransformer(xslSource);
        
        // transform the SAXSource to the result
        StreamResult result = new StreamResult("properties.xml");        
        transformer.transform( saxSource, result);
	}
}
