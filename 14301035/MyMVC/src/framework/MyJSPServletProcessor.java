package framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import serverTool.Request;
import serverTool.Response;

import com.sun.tools.javac.Main;

public class MyJSPServletProcessor {
	private static com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main(); 

	
	public void process(ModelAndView mv, Request request, Response response) {
		/*=========================JSP parse====================================*/
		//String uri = request.getRequestURI();
		String destJSP = mv.getViewName();
		JSPParser jParser = new JSPParser(Constants.JSP_ROOT + File.separator + destJSP + ".jsp");
		jParser.parseJSP(mv);
		String filename = jParser.writeToJavaFile();
		
		
		/*=========================.java -> .class==============================*/

		String[] args = new String[] {"-d", "./bin", Constants.JSP_SERVLET_ROOT + filename}; 
		System.out.println("Compile Start");
		int status = Main.compile(args); 
		System.out.println("State: " + status);
        System.out.println( status == 0 ? "恭喜编译成功" : "对不起编译失败");

		
		/*=========================Servlet load=================================*/
//		String servletURL = uri.substring(uri.lastIndexOf("/") + 1, uri.length()-4);
		String servletURL = destJSP;

        
		// 类加载器，用于从指定JAR文件或目录加载类
		URLClassLoader loader = null;
		try {
			URLStreamHandler streamHandler = null;
			// 创建类加载器
			loader = new URLClassLoader(new URL[] { new URL(null, "file:"
					+ Constants.JSP_CLASS_ROOT, streamHandler) });
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//new ByteArrayInputStream(classTxt.getBytes(StandardCharsets.UTF_8));

		String sclass = "JSPServlet." + servletURL;
		Class<?> myClass = null;
		try {
			// 加载对应的servlet类
			//myClass = loader.loadClass(sclass);
			System.out.println("Find class");
			//System.out.println(myClass.toString());
			myClass = Class.forName(sclass);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		Servlet servlet = null;

		try {
			// 生产servlet实例
			servlet = (Servlet) myClass.newInstance();
			// 执行servlet的service方法
			response.setContentType("text/html;charset=UTF-8");		

			servlet.service((ServletRequest) request, (ServletResponse) response);
			System.out.println("service Finish");	
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}

	}
}
