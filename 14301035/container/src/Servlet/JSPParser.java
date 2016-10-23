package Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class JSPParser {
	private String filename = null; //xxx(testJSP)
	private String jspstr = null;
	private String javastr = null;
	
	public JSPParser (String filename) { //filename: container/jsp/xxx.jsp
		this.filename = filename.substring(filename.lastIndexOf("/")+1, filename.length()-4);
		File myFile = new File(filename);
		try {
			//InputStreamReader read = new InputStreamReader(new FileInputStream(myFile),"UTF-8");
			FileInputStream fis = new FileInputStream(myFile);
			int size = fis.available();
			byte[] buffer = new byte[size];
			fis.read(buffer);
			fis.close();
			jspstr = new String(buffer, "UTF-8");
			
			//System.out.println(jspstr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public boolean parseJSP() {
		System.out.println("PARSE");
		String step1 = jspstr.replaceAll("<%=(.*?)%>", "\");out.write($1);out.write(\"");
		//System.out.println("Step1: " + step1);
		String step2 = step1.replaceAll("<%", "\");");
		//System.out.println("Step2: " + step2);
		String step3 = step2.replaceAll("%>", "out.write(\"");
		//System.out.println("Step3: " + step3);
		String finished = "out.write(\"" + step3 + "\");";
		//System.out.println("Finish: " + finished);
		javastr = finished.replaceAll("\r\n", "");
		
		return true;
	}
	
	
	public String writeToJavaFile () {
		String filename = null;
		System.out.println("writeToFile Start");
		try {
			filename = "/"+ this.filename + ".java";
			File f = new File(Constants.JSP_SERVLET_ROOT + filename);
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			//FileWriter fw = new FileWriter(new File(Constants.JSP_SERVLET_ROOT + filename));
			
			FileInputStream fis = new FileInputStream(new File(Constants.WEB_ROOT + "/header.txt"));
			byte[] buf = new byte[fis.available()];
			fis.read(buf);
			fis.close();
			String header = new String(buf);
			header = header.replaceAll("SERVLETNAME", this.filename);//TODO
			fw.write(header);
			
			fw.write(javastr);
			

			FileInputStream fis2 = new FileInputStream(new File(Constants.WEB_ROOT + "/tail.txt"));
			byte[] buff = new byte[fis2.available()];
			fis2.read(buff);
			fis2.close();
			String tail = new String(buff);
			fw.write(tail);
			fw.flush();
			
			fw.close();
			System.out.println("writeToFile Finish");

			return filename;
		} catch (IOException e) {
			e.printStackTrace();
			return filename;
		}
	}
	
	public String getJspstr() {
		return jspstr;
	}

	public void setJspstr(String jspstr) {
		this.jspstr = jspstr;
	}

	
	public static void main(String[] argv) {
		String filename = "E:/JAVAEE/testJSP.jsp";
		JSPParser jp = new JSPParser(filename);
		jp.parseJSP();
		jp.writeToJavaFile();
	}
}
