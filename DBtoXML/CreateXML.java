import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CreateXML extends Object {

   public static void main (String args[]){

	   //Create the Document object   
      Document mapDoc = null;
	  //Define a new Document object
      Document dataDoc = null;
      //Create the new Document
      Document newDoc = null;
      try {
         //Create the DocumentBuilderFactory
         DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
         //Create the DocumentBuilder
         DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
         //Parse the file to create the Document
         mapDoc = docbuilder.parse("E:\\xml\\mapping.xml");
		 //Instantiate a new Document object
         dataDoc = docbuilder.newDocument();

         //Instantiate the new Document
         newDoc = docbuilder.newDocument();
	  } catch (Exception e) {
         System.out.println("Problem creating document: "+e.getMessage());
      }

	  //Retrieve the root element 
      Element mapRoot = mapDoc.getDocumentElement();
      //Retrieve the (only) data element and cast it to Element
      Node dataNode = mapRoot.getElementsByTagName("data").item(0);
      Element dataElement = (Element)dataNode;
      //Retrieve the sql statement
      String sql = dataElement.getAttribute("sql");
	  
	  //Output the sql statement
      System.out.println(sql);
	   
      //For the JDBC-ODBC bridge, use
      //driverName = "sun.jdbc.odbc.JdbcOdbcDriver"
      //and
      //connectURL = "jdbc:odbc:pricing"
      String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
      String connectURL = "jdbc:odbc:ordersDSN";
      Connection db = null;	   
      //Create the ResultSetMetaData object, which will hold information about
      //the ResultSet
      ResultSetMetaData resultmetadata = null;
	  
	  //Create a new element called "data"	  
      Element dataRoot = dataDoc.createElement("data");

      try {
         Class.forName(driverName);
         db = DriverManager.getConnection(connectURL);
      } catch (ClassNotFoundException e) {
         System.out.println("Error creating class: "+e.getMessage());
      } catch (SQLException e) {
         System.out.println("Error creating connection: "+e.getMessage());
      }
   
      //Create the Statement object, used to execute the SQL statement
      PreparedStatement statement = null;
      //Create the ResultSet object, which ultimately holds the data retreived
      ResultSet resultset = null;
      try {
         statement = db.prepareStatement("select * from products");
         //Execute the query to populate the ResultSet
         resultset = statement.executeQuery();
         
		 //Get the ResultSet information
         resultmetadata = resultset.getMetaData();
         //Determine the number of columns in the ResultSet
         int numCols = resultmetadata.getColumnCount();
		 
		 //Check for data by moving the cursor to the first record (if there is one)
         while (resultset.next()) {
            //For each row of data
            //Create a new element called "row"
            Element rowEl = dataDoc.createElement("row");
            for (int i=1; i <= numCols; i++) {
               //For each column index, determine the column name
               String colName = resultmetadata.getColumnName(i);
               //Get the column value
               String colVal = resultset.getString(i);
			   //Determine if the last column accessed was null
               if (resultset.wasNull()) {
                  colVal = "and up";
               }
               //Create a new element with the same name as the column
               Element dataEl = dataDoc.createElement(colName);
               //Add the data to the new element
               dataEl.appendChild(dataDoc.createTextNode(colVal));
               //Add the new element to the row
               rowEl.appendChild(dataEl);
            }
            //Add the row to the root element			
            dataRoot.appendChild(rowEl);

         }
      } catch (SQLException e) {
         System.out.println("SQL Error: "+e.getMessage());
      } finally {
         System.out.println("Closing connections...");
         try {
            db.close();
         } catch (SQLException e) {
            System.out.println("Can't close connection.");
         }
	  }
	  //Add the root element to the document
      dataDoc.appendChild(dataRoot);
      //Retrieve the root element 
      Element newRootInfo = (Element)mapRoot.getElementsByTagName("root").item(0);
      //Retrieve the root and row information
      String newRootName = newRootInfo.getAttribute("name");
      String newRowName = newRootInfo.getAttribute("rowName");
      //Retrieve information on elements to be built in the new document
      NodeList newNodesMap = mapRoot.getElementsByTagName("element");

	  //Create the final root element with the name from the mapping file
      Element newRootElement = newDoc.createElement(newRootName);
	  
      //Retrieve all rows in the old document
      NodeList oldRows = dataRoot.getElementsByTagName("row");
      for (int i=0; i < oldRows.getLength(); i++){
	   
         //Retrieve each row in turn
         Element thisRow = (Element)oldRows.item(i);
		 
         //Create the new row.....
         Element newRow = newDoc.createElement(newRowName);
         			   
         for (int j=0; j < newNodesMap.getLength(); j++) {
             
            //For each node in the new mapping, retrieve the information 			 
            //First the new information...  
            Element thisElement = (Element)newNodesMap.item(j); 
            String newElementName = thisElement.getAttribute("name");	

            //Then the old information
            Element oldElement = (Element)thisElement.getElementsByTagName("content").item(0);
            String oldField = oldElement.getFirstChild().getNodeValue();


            //Get the original values based on the mapping information
            Element oldValueElement = (Element)thisRow.getElementsByTagName(oldField).item(0);
            String oldValue = oldValueElement.getFirstChild().getNodeValue();
			
            //Create the new element
            Element newElement = newDoc.createElement(newElementName);
            newElement.appendChild(newDoc.createTextNode(oldValue));
            
			//Retrieve list of new elements
            NodeList newAttributes = thisElement.getElementsByTagName("attribute");
            for (int k=0; k < newAttributes.getLength(); k++) {
               //For each new attribute
               //Get the mapping information
               Element thisAttribute = (Element)newAttributes.item(k);
               String oldAttributeField = thisAttribute.getFirstChild().getNodeValue();
               String newAttributeName = thisAttribute.getAttribute("name");

               //Get the original value
               oldValueElement = (Element)thisRow.getElementsByTagName(oldAttributeField).item(0);
               String oldAttributeValue = oldValueElement.getFirstChild().getNodeValue();
		
               //Create the new attribute		
               newElement.setAttribute(newAttributeName, oldAttributeValue);
            }
            
			//Add the new element to the new row
            newRow.appendChild(newElement);

         }
         //Add the new row to the root
         newRootElement.appendChild(newRow);
      }  
      //Add the new root to the document
      newDoc.appendChild(newRootElement);
	  
	  System.out.println(newRootElement.toString());
	  
	  //We can also save this to a file.....
	  //File f=new File("List.xml");
	  
   }
}