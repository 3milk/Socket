package framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;





import javax.servlet.http.HttpServletRequest;

import serverTool.Request;
import serverTool.Response;
import test.test;
import dev.factory.AnnotationParser;
import dev.factory.AnnotationParser.Controller;
import dev.factory.AnnotationParser.RequestMapping;
import dev.factory.BeanFactory;
import dev.factory.XMLBeanFactory;
import dev.resource.LocalFileResource;

public class DispatcherServlet implements Servlet{
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
        System.out.println("from dispatcher HTTP service");
		
		LocalFileResource resource = new LocalFileResource("bean.xml");	
		BeanFactory beanFactory = null;
		try {
			beanFactory = new XMLBeanFactory(resource);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
		//跟controller有毛线关系
		test test = (test) beanFactory.getBean("test");
		Class cls = test.getClass();
		//识别路径对应方法，并调用返回mv
		AnnotationParser ap = new AnnotationParser();
        Map<String, Method> requestMapping = ap.getRequestMapping(cls);
//        String url = request.getRequestURI();
        String url = ((Request)request).getUri();
        Method method = requestMapping.get(url);
        ModelAndView mv = new ModelAndView((Request)request);
        try {
			mv = (ModelAndView) method.invoke(test, mv);///////////第二个mv更改?
			System.out.println("Dispatcher: mv viewname: " + mv.getViewName());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
        
      //步骤5 步骤6、解析视图并进行视图的渲染  
      //步骤5 由ViewResolver解析View（viewResolver.resolveViewName(viewName, locale)）  
      //步骤6 视图在渲染时会把Model传入（view.render(mv.getModelInternal(), request, response);）  
        if (mv != null ) { //&& !mv.wasCleared() 
            MyJSPServletProcessor render = new MyJSPServletProcessor();
        	render.process(mv, (Request)request, (Response)response);  
        }  
        //System.out.println(boss.toString()); 
		
		/*////////////////???
		PrintWriter out = response.getWriter();
				request.setCharacterEncoding("utf-8");out.write("<html><head><title>Hello World</title></head><body>Hello World!<br/><p>   TODAY IS: ");out.write( (new java.util.Date()).toLocaleString());out.write("</p>");out.println("Your IP address is " + request.getRemoteAddr());out.write("</body></html>");out.flush();
		out.close();
		*/
	
	}
	
}
