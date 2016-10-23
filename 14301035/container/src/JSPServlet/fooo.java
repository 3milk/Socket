package JSPServlet;

import javax.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

public class fooo implements Servlet {

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		//System.out.println("from service");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");out.write("<html>  "); int a = 3; out.write("  <body>    "); for (int i = 0; i < a; i++) {out.write("	<h1>");out.write( "HELLO" + i );out.write("</h1>	"); } out.write("  </body></html>");out.flush();
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