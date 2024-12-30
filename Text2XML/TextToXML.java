import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;



/*
 * Created on feb 23, 2007
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
public class TextToXML

{
	public static void main(String[] args) {
		try {
			File file = new File("cookies.txt");
			String str="";
			String str1="<XML><Text>";
            //Reading the Message
			FileInputStream fis=new FileInputStream(file);
     	    InputStreamReader isr = new InputStreamReader( fis );
		    BufferedReader br = new BufferedReader( isr );

		    File f1=new File("cookies.xml");
			FileOutputStream fis1=new FileOutputStream(f1);
			PrintStream ps=new PrintStream(fis1);
			while((str=br.readLine())!=null)
			{
				//if(str=="\n")
				//ps.println("</Paragraph><Paragraph>");	
			     str1+=str;       
				System.out.println(str);
			}
			str1+="</Text></XML>";
			ps.println(str1); 
			
			System.out.println("Done");	
			
				
			}
		catch(IOException io) {
			io.printStackTrace();
		}
	    }

}
