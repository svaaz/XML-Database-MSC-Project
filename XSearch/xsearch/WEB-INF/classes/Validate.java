import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Validate extends HttpServlet 

{

public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException

	{
String result="";
String str=request.getParameter("string");
System.out.println("**************************");
System.out.println("The Search String is:"+str);
System.out.println("**************************");
int counter=0;
System.out.println("**************************"+counter);
PrintWriter out=response.getWriter();
{}
if(str.equals(""))
		{
response.sendRedirect("index.html");
	}

else

{	FilesUtil f=new FilesUtil();
	try
	{
	result=f.search(str);
	//System.out.println(result);
	}catch(Exception e){}

	//System.out.println("<Html><img src=banner.GIF text=banner.gif><br><h1>"+result);

	if (result.equals("null"))
		response.sendRedirect("noresults.html");
	else
		response.sendRedirect("complete.html");

		//out.println("<html><img src=banner.jpg> <br><hr><h3>Your Search :  "+str+"</h3>"+result);

}
	}




}
