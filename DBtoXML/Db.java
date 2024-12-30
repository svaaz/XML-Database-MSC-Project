/*
 * Created on jan 22 2006
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

//This Program Converts any Given Database Table into XML File
//It uses Resultset Metadata 
//this approach is very slow for large table

public class Db
{
 

public static void main(String[] args) 
	{
		
		try{
			//Check for  the Driver
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			// Data source
			Connection con1 = DriverManager.getConnection("jdbc:odbc:mydsn");
			
			// Create a Scrollable Statement
			Statement st1 = con1.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
			
			//Resultset 
			ResultSet rs;
			
			//Create the ResultSetMetaData object, which will hold information about
		    //the ResultSet
			
		    ResultSetMetaData resultmetadata = null;
		    
		    //Specify the Query for the Table
			rs = st1.executeQuery("SELECT * FROM suppliers");
			
			//rs.first();
			 //Get the ResultSet information
	        resultmetadata = rs.getMetaData();
	        
	        //Determine the No of Columns in the table
	        int numCols = resultmetadata.getColumnCount();
	        
	        //Create a column array to hold the Columns in the Table
	        String column[]=new String[numCols+1];
	        
	        //Store the XML as a String
	        String XMLcont="<XML>";
	        
	        //Integer varible for keeping track of the No of Rows 
	        int count=1;
	        
	        //Loop through each column in the table
	        for(int i=1;i<=numCols;i++)
	        {
	        	//Print Each Column Name
	        	column[i]=resultmetadata.getColumnName(i);
	        	System.out.println("Column :"+i+column[i]);
	        	
	        }
	        
	        //Add each Row to the XML File
	         while(rs.next())
	         {	XMLcont+="<Row>";	
	        	for(int i=1;i<=numCols;i++)
	        	{
	        	
	        	XMLcont+="<"+column[i]+">"+rs.getObject(column[i])+"</"+column[i]+">";	
	        	
	        	// System.out.println("<"+column[i]+">"+rs.getObject(column[i])+"</"+column[i]+">");
	        	}
	        	XMLcont+="</Row>";
	        	count++;
	         }
	         XMLcont+="</XML>";
	         System.out.println(XMLcont);
	        
	        //writing to an XML FILE
	         File XMLFile=new File("XMLFILE.xml");
	         BufferedWriter out = new BufferedWriter(new FileWriter(XMLFile));
	         out.write(XMLcont);
			 out.newLine();
			 out.flush();
			 
			 //Displaying the created XML File 
			 
			 Runtime r=Runtime.getRuntime();
			 String d[]=new String [5];
			 
			 //r.exec("C:\\Program Files\\Internet Explorer\\iexplore.exe");

	       

		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

	
}
