/*
 * Created on Jan 24, 2007
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
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import org.w3c.tidy.Tidy;


public class TestHTML2XML {
private String url;
private String outFileName;
private String errOutFileName;

public TestHTML2XML(String url, String outFileName, String
errOutFileName) {
this.url = url;
this.outFileName = outFileName;
this.errOutFileName = errOutFileName;
}

public void convert() {
URL u;
BufferedInputStream in;
FileOutputStream out;

Tidy tidy = new Tidy();

//Tell Tidy to convert HTML to XML
tidy.setXmlOut(true);

try {
//Set file for error messages
tidy.setErrout(new PrintWriter(new FileWriter(errOutFileName), true));
u = new URL(url);

//Create input and output streams
in = new BufferedInputStream(u.openStream());
out = new FileOutputStream(outFileName);

//Convert files
tidy.parse(in, out);

//Clean up
in.close();
out.close();

} catch (IOException e) {
System.out.println(this.toString() + e.toString());
}
}

public static void main(String[] args) {
/*
* Parameters are:
* URL of HTML file
* Filename of output file
* Filename of error file
*/
	
TestHTML2XML t = new TestHTML2XML("file:///E:/New%20Folder/xmlDBProj/n.html","myxml.xml" ,"error.txt" );
t.convert();
}
}