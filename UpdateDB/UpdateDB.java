/*
 * Created on Dec 12, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author srini
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.sql.*;


public class UpdateDB extends Object 
{
	public static void main (String args[])
	{
		//Determine the file location
		String ordersFile = "E:\\xml\\orders.xml";
		//Create the XML document
		Document ordersDoc = null;
		
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String connectURL = "jdbc:odbc:ordersDSN";
		Connection db = null;
		
		try {
			//creATE  the parser and parse the file
			DocumentBuilderFactory docbuilderfactory =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder =docbuilderfactory.newDocumentBuilder();
			ordersDoc = docbuilder.parse(ordersFile);
		} catch (Exception e) {
			System.out.println("Cannot read the file: "+e.getMessage());
		}
		
		
		try {
			Class.forName(driver);
			try
			{
			db = DriverManager.getConnection(connectURL);
			
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			Element ordersRoot = ordersDoc.getDocumentElement();
			NodeList orders = ordersRoot.getElementsByTagName("order");
			
			for (int i = 0; i < orders.getLength(); i++) {
				//			For each order, get the order element
				Element thisOrder = (Element)orders.item(i);
				//			Get order information
				String thisOrderid = thisOrder.getAttribute("orderid");
				System.out.println("Order: " + thisOrderid);
				//			Get customer information
				String thisCustomerid =
					thisOrder.getElementsByTagName("customerid")
					.item(0)
					.getFirstChild().getNodeValue();
				System.out.println("Customer: " + thisCustomerid);
				//			Loop through each product for the order
				NodeList products = thisOrder.getElementsByTagName("product");
				for (int j=0; j < products.getLength(); j++) {
					//			Retrieve each product
					Element thisProduct = (Element)products.item(j);
					//			Get product information from attributes and child
					//			elements
					String thisProductid = thisProduct.getAttribute("productid");
					System.out.println(" Product: " + thisProductid);
					String priceStr = thisProduct.getElementsByTagName("price")
					.item(0)
					.getFirstChild().getNodeValue();
					System.out.println(" Price: " + priceStr);
					String qtyStr = thisProduct.getElementsByTagName("qty")
					.item(0)
					.getFirstChild().getNodeValue();
					System.out.println(" Quantity: " + qtyStr);
					System.out.println();
					//Create and execute the sql statement
					String sqlTxt = "insert into orders "+
					"(orderid, userid, productid, unitprice, quantity) "+
					"values ("+thisOrderid+", "+thisCustomerid+", '"+
					thisProductid+"', "+priceStr+", "+qtyStr+")";
					try
					{
						Statement statement = db.createStatement();
						int results = statement.executeUpdate(sqlTxt);	
					}
					catch(SQLException e){
						e.printStackTrace();
					}
					
				}
			
			
			
				}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error creating class: "+e.getMessage());
		}
			
		
			
		}
}
