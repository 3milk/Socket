package Servlet;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import Servlet.Constants;
import Servlet.Request;
import Servlet.Response;

public class MyServletProcessor {

	public void process(Request request, Response response) {

		String uri = request.getUri();
//		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		String servletURL = uri.substring(uri.lastIndexOf("/") + 1);
	
		// 类加载器，用于从指定JAR文件或目录加载类
		URLClassLoader loader = null;
		try {
			URLStreamHandler streamHandler = null;
			// 创建类加载器
			loader = new URLClassLoader(new URL[] { new URL(null, "file:"
					+ Constants.WEB_SERVLET_ROOT, streamHandler) });
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		Class<?> myClass = null;
		try {
			// 加载对应的servlet类
			String sclass = XMLParse.findClassName(servletURL);//servletURL
			myClass = loader.loadClass(sclass);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		Servlet servlet = null;

		try {
			// 生产servlet实例
			servlet = (Servlet) myClass.newInstance();
			// 执行servlet的service方法
			servlet.service((ServletRequest) request, (ServletResponse) response);
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}

	}
}