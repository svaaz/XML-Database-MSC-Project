import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.*;

/*
 * Created on Feb 21, 2007
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
public class FilesUtil
{
public String filetosting(File f) throws Exception
{
	
	String contents="";
	String str=null;
	FileInputStream fis=new FileInputStream(f);
	InputStreamReader isr=new InputStreamReader(fis);
	BufferedReader br=new BufferedReader(isr);
	
	while((str=br.readLine())!=null)
	contents+=str;	
	
	//System.out.println(contents);
	return contents;

}

public String search(String args) throws Exception
{
	FilesUtil l=new FilesUtil();
	String filelist[]=new String[100];
	//new operations on....
	String dir="e:\\xml\\";
	String contents="";
	String results="Your Search is : "+args;
	int j=0;
	int count=0;
	boolean flag=false;
	File f=new File (dir);
	Indexer ind=new Indexer();
	if(f.isDirectory())
	{
		filelist=f.list();
	}
	for(int i=0;i<filelist.length;i++)
	{
		//System.out.println(filelist[i]);
		contents=l.filetosting(new File(dir+filelist[i]));
		KMP k=new KMP();
		j=k.KMPSearch(contents,args);


		// j=contents.indexOf(args);
		 //System.out.println("Index:"+j);
		 if(j>0)
		 {
		ind.updateindex(args,filelist[i]);
		 count++;	
		 results+="<br><br><Font type=verdana size=4><b>Result : "+count+" </b><br>File name :"+dir+filelist[i]+"  <br>No of mathces : "+j+"<br>Link to File:<i>"+dir+filelist[i]+"</i></font>";
		 	flag=true;
		 
		 }
		
		
	}

	System.out.println("Creating out file");
try
{
	PrintWriter out=new PrintWriter(new FileOutputStream(new File("C://Program Files//Apache Software Foundation//Tomcat 5.5//webapps//xsearch//out.html")));	
	System.out.println(results);
	out.println(results);
	out.flush();
	out.close();
}
catch(Exception e22)
{
}

	if (flag==true)

		return "found";
		
		else
			
		return "null";

	//new operations end...

	
}
public static void main(String a[]) throws Exception
{
	FilesUtil fu=new FilesUtil();
	fu.search("g");
	
}

}
