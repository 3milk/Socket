package JSPServlet;

import javax.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

public class test implements Servlet {

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
					+ "\r\n";out.write(headMessage + "<html><head><title>Insert title here</title></head><body><h1>这就是是一个简单的测试，就是根据名字来找到文件！</h1>用户名：");out.write("233");out.write("密码：");out.write("244");out.write("</body></html>");out.flush();
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