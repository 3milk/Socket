package framework;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class XMLParse {
	
	/*
	 * 读取xml文件，根据url返回servlet类名
	 * =。=其实逻辑最好是在服务器启动的时候就解析好xml,
	 * =。=而不是每一次url请求都读一次xml文件
	 * */
	public static String findClassName(String url) {
		long lasting = System.currentTimeMillis();
		try {
			File f = new File("web.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList servletMapping = doc
					.getElementsByTagName("servlet-mapping");
			NodeList servletName = doc.getElementsByTagName("servlet-name");
			String searchedName = null;


			for (int i = 0; i < servletMapping.getLength(); i++) {
				String urlPattern = doc.getElementsByTagName("url-pattern")
						.item(i).getFirstChild().getNodeValue();
				//System.out.println("urlPattern:" + urlPattern);
				//System.out.println("url:" + url);

				if (urlPattern.equals('/'+url)) {
					//System.out.println("i: "+ i);
					String sname = doc.getElementsByTagName("servlet-name")
							.item(2*i).getFirstChild().getNodeValue();
					//System.out.println("servlet-name:" + sname);
					searchedName = sname;
					break;
				}
			}

			for (int i = 0; i < servletName.getLength(); i++) {
				String sname = doc.getElementsByTagName("servlet-name").item(i)
						.getFirstChild().getNodeValue();
				//System.out.println("servlet-name:" + sname);
				if (sname.equals(searchedName)) {
					String sclass = doc.getElementsByTagName("servlet-class")
							.item(i/2).getFirstChild().getNodeValue();
					//System.out.println("servlet-class:" + sclass);
					return sclass;
					//break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
