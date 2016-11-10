package JSPServlet;

import javax.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

public class testJSP implements Servlet {

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		//System.out.println("from service");
		PrintWriter out = response.getWriter();out.write("<html><head><title>Hello World</title></head><body>Hello World!<br/><p>   TODAY IS: ");out.write( (new java.util.Date()).toLocaleString());out.write("</p>");out.println("Your IP address is " + request.getRemoteAddr());out.write("</body></html>");out.flush();
out.close();
}

	public void destroy() {
		System.out.println("destroy");
	}

	public String getServletInfo() {
		return null;
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}