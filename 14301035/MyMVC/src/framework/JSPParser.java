package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

public class JSPParser {
	private String filename = null; //xxx(testJSP)
	private String jspstr = null;
	private String javastr = null;
	
	public JSPParser (String filename) { //filename: container/jsp/xxx.jsp
		System.out.println("JSPParser: constructor: filename " + filename);
		this.filename = filename.substring(filename.lastIndexOf("\\")+1, filename.length()-4);
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
			System.out.println("JSPParser: writeToJavaFile: filename " + filename);
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

	//TEST
	public static void main(String[] argv) {
		String filename = "E:/JAVAEE/testJSP.jsp";
		JSPParser jp = new JSPParser(filename);
		jp.parseJSP();
		jp.writeToJavaFile();
	}


	public boolean parseJSP(ModelAndView mv) {
		System.out.println("PARSE mv");
//		String step0 = jspstr.replaceAll("${.*?}", "\");out.write(mv.getMap($1));out.write(\"");
		
		String step00 = jspstr.replaceAll("\"", "\\\\\"");

		
		int nextStart = 0;
		String step0 = step00;
//error		for(int dollarStart = 0; (nextStart = jspstr.indexOf("$", dollarStart)) > 0; dollarStart = nextStart+1) {
		for(;(nextStart = step0.indexOf("$")) > 0; ) {
     		String part1 = step0.substring(0, nextStart);//不包括nextStart位
			String part2 = step0.substring(nextStart, step0.length());
			int indexOfRight = part2.indexOf("}");
			String part3 = part2.substring(indexOfRight+1, part2.length());
			part2 = part2.substring(0, indexOfRight+1);
			/*
			System.out.println("JSPParser: parseJSP: part1 :\r\n" + part1);
			System.out.println("JSPParser: parseJSP: part2 :\r\n" + part2);
			System.out.println("JSPParser: parseJSP: part3 :\r\n" + part3);
			System.out.println("JSPParser: parseJSP: nextStart :\r\n" + nextStart + " || "+ indexOfRight + " || " + jspstr.length());
			*/

			String key = part2.substring(2, part2.length()-1); //${name}
			String value = (String) mv.getMap(key);
			/*
			Map<String, String> paras = mv.getModel();
			for(Map.Entry<String, String> entry: paras.entrySet()) {
		    	System.out.println("JSPParser: " + entry.getKey() + " || " + entry.getValue());
		    }*/
			
			
//			String replacePart2 = "\");out.write(\"" + value + "\");out.write(\"";
			step0 = part1 + "\");out.write(\"" + value + "\");out.write(\"" + part3;
			
			//System.out.println("JSPParser: parseJSP: step0 :\r\n" + step0);

		}
		
//		String step01 = step0.replaceAll("\"", "\\\\\"");
		
		System.out.println("JSPParser: ParseJSP: " + step00);
		
		////////其实底下的step都不会被执行= =
	    String step1 = step0.replaceAll("<%=(.*?)%>", "\");out.write($1);out.write(\"");
		//System.out.println("Step1: " + step1);
		String step2 = step1.replaceAll("<%", "\");");
		//System.out.println("Step2: " + step2);
		String step3 = step2.replaceAll("%>", "out.write(\"");
		//System.out.println("Step3: " + step3);
		String finished = "out.write(headMessage + \"" + step3 + "\");";
		//System.out.println("Finish: " + finished);
		javastr = finished.replaceAll("\r\n", "");
		
		return true;		
	}
}
