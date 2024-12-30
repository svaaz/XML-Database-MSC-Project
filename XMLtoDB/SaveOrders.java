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


public class SaveOrders extends Object 
{
	public static void main (String args[])
	{
		//Determine the file location
		String ordersFile = "E:\\xml\\orders.xml";
		//Create the XML document
		Document ordersDoc = null;
		//		For the JDBC-ODBC bridge, use
		//	String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
		//		and
		//		connectURL = "jdbc:odbc:orders";
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String connectURL = "jdbc:odbc:myother";
		Connection db = null;
		String Orderid=null;
		String Productid="default";
		
		try {
			//Instantiate the parser and parse the file
			DocumentBuilderFactory docbuilderfactory =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder =docbuilderfactory.newDocumentBuilder();
			ordersDoc = docbuilder.parse(ordersFile);
		} catch (Exception e) {
			System.out.println("Cannot read the orders file: "+e.getMessage());
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
			NodeList orders = ordersRoot.getElementsByTagName("Row");
			System.out.println(orders.getLength());
			for (int i = 0; i < orders.getLength(); i++) {
				//			For each order, get the order element
				Element thisOrder = (Element)orders.item(i);
				//			Get order information
				String thisOrderid = thisOrder.getAttribute("orderid");
				System.out.println("Order: " + thisOrderid);
				//			Get customer information
				String thisCustomerid =
					thisOrder.getElementsByTagName("userid")
					.item(0)
					.getFirstChild().getNodeValue();
				System.out.println("Customer: " + thisCustomerid);
				//			Loop through each product for the order
				NodeList products = thisOrder.getElementsByTagName("product");
				//for (int j=0; j < products.getLength(); j++) {
					//			Retrieve each product
					//Element thisProduct = (Element)products.item;
					//			Get product information from attributes and child
					//			elements
					String thisProductid = thisOrder.getAttribute("productid");
					System.out.println(" Product: " + thisProductid);
					String priceStr = thisOrder.getElementsByTagName("unitprice")
					.item(0)
					.getFirstChild().getNodeValue();
					System.out.println(" Price: " + priceStr);
					String qtyStr = thisOrder.getElementsByTagName("quantity")
					.item(0)
					.getFirstChild().getNodeValue();
					System.out.println(" Quantity: " + qtyStr);
					System.out.println();
					//Create and execute the SQL statement
					String sqlTxt = "insert into orders "+
					"(orderid, userid, productid, unitprice, quantity) "+
					"values ("+Orderid+", "+thisCustomerid+", '"+
					Productid+"', "+priceStr+", "+qtyStr+")";
					System.out.println(sqlTxt);
					try
					{
						Statement statement = db.createStatement();
						int results = statement.executeUpdate(sqlTxt);	
					}
					catch(SQLException e){
						e.printStackTrace();
					}
					
				
			
			
			
				}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error creating class: "+e.getMessage());
		}
			
		
			
		}
}
