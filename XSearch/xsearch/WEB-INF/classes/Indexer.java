import java.io.*;
import java.sql.*;

//incomplete..... more operations required

//optimize performance

public class Indexer
{
 

public void updateindex(String word,String document) 
	{
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con1 = DriverManager.getConnection("jdbc:odbc:index");
			Statement st1 = con1.createStatement();
			ResultSet rs;


			 st1.executeUpdate("insert into Index values('" + word + "','" + document + "')" );
			
			}

			catch(Exception e)
				{
				}

}
}