package dev.factory;

import java.awt.Component;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.bean.BeanDefinition;
import dev.bean.PropertyValue;
import dev.bean.PropertyValues;

public class AnnotationParser {
	private String packagePath;
	private List<String> fileNames; 
	
	public AnnotationParser () {
		this.packagePath = "";
		this.fileNames = null;
	}
	
	public AnnotationParser (String packageName) { //test.car
		this.getClassName(packageName, true);
	}
	
	@Target(value=ElementType.TYPE)
	@Retention(value=RetentionPolicy.RUNTIME)
	@Documented
	public @interface Component{
	    //String name() default "";
		String value() default "";
	}

	@Target(value={ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
	@Retention(value=RetentionPolicy.RUNTIME)
	@Documented
	public @interface Autowired{
		boolean required() default true;
	}

	
	/** 
     * 获取某包下所有类 
     * @param packageName 包名 
     * @param childPackage 是否遍历子包 
     * @return 类的完整名称 
     */  
    public void getClassName(String packageName, boolean childPackage) {  
        ClassLoader loader = Thread.currentThread().getContextClassLoader();  
        String packagePath = packageName.replace(".", "/");  
        URL url = loader.getResource(packagePath);  
        if (url != null) {  
            String type = url.getProtocol();  
            if (type.equals("file")) {  
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);  
            }  
        }  
    } 
    /** 
     * 从项目文件获取某包下所有类 
     * @param filePath 文件路径 
     * @param className 类名集合 
     * @param childPackage 是否遍历子包 
     * @return 类的完整名称 
     */ 
    private List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {  
        List<String> myClassName = new ArrayList<String>();  
        File file = new File(filePath);  
        File[] childFiles = file.listFiles();  
        for (File childFile : childFiles) {  
            if (childFile.isDirectory()) {  
                if (childPackage) {  
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));  
                }  
            } else {  
                String childFilePath = childFile.getPath();  
                if (childFilePath.endsWith(".class")) {  
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\bin") + 5, childFilePath.lastIndexOf("."));  
                    childFilePath = childFilePath.replace("\\", ".");  
                    myClassName.add(childFilePath);  
                }  
            }  
        }  
        return myClassName;  
    }

    public Map<String, BeanDefinition> componentScan() {
		Map<String, BeanDefinition> componentBeanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

		for(String file: fileNames) {
			try {			
				Class<?> cls = Class.forName(file);
				Component component = cls.getDeclaredAnnotation(Component.class);
				if(component != null) {
					/////////////////////class//////////////////////////
					String[] id = file.split("\\.");
					BeanDefinition beandef = new BeanDefinition();
					beandef.setBeanClassName(file);
					beandef.setBeanClass(cls);

					/////////////////////property////////////////////////
	        		PropertyValues propertyValues = new PropertyValues();
	        		Object obj = cls.newInstance();
					Field[] fields = cls.getDeclaredFields();//security problem
					for(Field field: fields) {
						field.setAccessible(true);
						String name = field.getName();
						PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);  
			            Method getMethod = pd.getReadMethod();//获得get方法  
			            Object value = getMethod.invoke(obj);//执行get方法返回一个Object  
						
		        		propertyValues.AddPropertyValue(new PropertyValue(name, value));
					}
	            	beandef.setPropertyValues(propertyValues);		
					
	            	componentBeanDefinitionMap.put(id[id.length-1], beandef);
				}
			} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | InstantiationException | IntrospectionException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return componentBeanDefinitionMap;
	}

    public Object autowiredConstruct(XMLBeanFactory xmlBeanFactory, Class<?> cls) {
    	Object obj = null;
    	for(Constructor<?> c : cls.getDeclaredConstructors()) {
    		Autowired autowired = c.getDeclaredAnnotation(Autowired.class);
    		List<Object> args = new ArrayList<Object>();
    		if(autowired != null) {
    	    	//System.out.println("To be autoWired");

    			c.setAccessible(true);
    			for(Parameter p: c.getParameters()) {
    				//System.out.println(p.getType().getName());//TEST
    				String[] name = p.getType().getName().split("\\.");
    				args.add(xmlBeanFactory.getBean(name[name.length-1]));
    			}
    			try {
					obj = c.newInstance(args.toArray());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
    		}
    	}
		return obj;
    }
}
