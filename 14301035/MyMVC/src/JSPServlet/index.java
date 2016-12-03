package JSPServlet;

import javax.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

public class index implements Servlet {

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		//System.out.println("from service");
		PrintWriter out = response.getWriter();
		//request.setCharacterEncoding("utf-8");
		String headMessage = "HTTP/1.1 200 OK\r\n"
					+ "Content-Type: text/html; charset=utf-8\r\n" + "Content-Length: 4096\r\n"
					+ "\r\n";out.write(headMessage + "<html><head><title>Insert title here</title></head><body><form action=\"/hello\" method=\"post\"><table><tr><td>用户名</td><td><input type=\"text\" name=\"name\"></td></tr><tr><td>密码</td><td><input type=\"password\" name=\"pas\"></td></tr><tr><td><input type=\"submit\" value=\"提交\"></td><td><input type=\"reset\" value=\"取消\"></td></tr></table></form></body></html>");out.flush();
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