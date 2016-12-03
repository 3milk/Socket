package framework;

import java.util.*;

import serverTool.Request;

public class ModelAndView {
	private Map<String, Object> model;
	private Map view;//??
	private String viewName;
	private Request request;
	
	public ModelAndView() {
		this.model = new LinkedHashMap<String, Object>();
		this.view = new LinkedHashMap();
		this.viewName = null;
		this.request = null;
	}
	
	public ModelAndView(String viewName) {
		this.model = new LinkedHashMap<String, Object>();
		this.view = new LinkedHashMap();
		this.viewName = viewName;
		this.request = null;
	}

	public ModelAndView(Request request) {
		this.model = new LinkedHashMap<String, Object>();
		this.view = new LinkedHashMap();
		this.viewName = null;
		this.request = request;
		
		Map<String, String> paras = request.getMyParameterMap();
		model.putAll(paras);    
	    for(Map.Entry<String, String> entry: paras.entrySet()) {
	    	System.out.println(entry.getKey() + " || " + entry.getValue());
	    }
		
		/*Enumeration<String> attributes = request.getAttributeNames() ;
		for (;attributes.hasMoreElements();) {
			String attr = attributes.nextElement();
			Object value = request.getAttribute(attr);
			model.put(attr, value);
		}*///for httpServletRequest
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
		System.out.println("ModelAndView: setViewName " + viewName);
	}
	
	public ModelAndView addObject(String name, Object value) {
		//request.setAttribute(name, value);
		model.put(name, value);
		return this;
	}

	
	public Object getMap(String key) {
		String value = (String) model.get(key);
		System.out.println("ModelAndView: getMap: value: " + key + "||" + value);
		return value;
	}
	
	public Map getModel() {
		return model;
	}
}
