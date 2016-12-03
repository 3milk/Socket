package dev.factory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


import dev.bean.BeanDefinition;
import dev.bean.BeanUtil;
import dev.bean.PropertyValue;
import dev.bean.PropertyValues;
import dev.resource.Resource;
import dev.util.ReflectionUtils;

public class XMLBeanFactory extends AbstractBeanFactory{
	
	private String xmlPath;
	private AnnotationParser ap;
	
	public XMLBeanFactory(Resource resource) throws ClassNotFoundException
	{
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document document = dbBuilder.parse(resource.getInputStream());
			
			/*指定包路径下扫描@component*/
			//NodeList componentPackage = document.getElementsByTagName("component-scan");
            ap = new AnnotationParser("test");//TODO TODO
            Map<String, BeanDefinition> componentBeanDefinitionMap = ap.componentScan();
            for(Entry<String, BeanDefinition> entry:componentBeanDefinitionMap.entrySet()){    
                this.registerBeanDefinition(entry.getKey(), entry.getValue());    
            }
			
			//beanList处理
			NodeList beanList = document.getElementsByTagName("bean");
            for(int i = 0 ; i < beanList.getLength(); i++)
            {
            	Node bean = beanList.item(i);
            	BeanDefinition beandef = new BeanDefinition();
            	String beanClassName = bean.getAttributes().getNamedItem("class").getNodeValue();
            	String beanName = bean.getAttributes().getNamedItem("id").getNodeValue();
            	//System.out.println(beanName + "   beanclass:" + beanClassName);
        		beandef.setBeanClassName(beanClassName);
        		
        		////////普通bean
				try {
					Class<?> beanClass = Class.forName(beanClassName);
					beandef.setBeanClass(beanClass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		
        		PropertyValues propertyValues = new PropertyValues();
        		
        		NodeList propertyList = bean.getChildNodes();
            	for(int j = 0 ; j < propertyList.getLength(); j++)
            	{
            		Node property = propertyList.item(j);
            		if (property instanceof Element) {
        				Element ele = (Element) property;
        				
        				String name = ele.getAttribute("name");
        				Class<?> type;
						try {
							type = beandef.getBeanClass().getDeclaredField(name).getType();
							Object value = ele.getAttribute("value");

	        				if(type == Integer.class)
	        				{
	        					value = Integer.parseInt((String) value);
	        				}
	        				if(!value.equals("")) {
	        					propertyValues.AddPropertyValue(new PropertyValue(
	        						name,value));
	        				}
	        				Object refName = ele.getAttribute("ref");
	        				if(!refName.equals("")) {
	        					//Class<?> refClass = Class.forName("test." + (String) refName);//TODO TODO
	        					//Object ref = refClass.newInstance();//属性值未注入?
	        					Object ref = this.getBean((String) refName);
	        					propertyValues.AddPropertyValue(new PropertyValue(
		        						name,ref));
	        				}//可能属性值未注入？
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
        			}
            	}
            	beandef.setPropertyValues(propertyValues);
            	
            	this.registerBeanDefinition(beanName, beandef);
            }
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) {
		
		try {
			// set BeanClass for BeanDefinition
			
			Class<?> beanClass = beanDefinition.getBeanClass();

			// set Bean Instance for BeanDefinition
			//Object bean = ap.autowiredConstruct(this, beanClass);//使用autowire自动装配
			Object bean = beanDefinition.getBean();
			if(bean == null) {
			    bean = beanClass.newInstance();	//调用默认构造器
			}
			if(bean == null) {
				System.out.println("NULL");
			}

			List<PropertyValue> fieldDefinitionList = beanDefinition.getPropertyValues().GetPropertyValues();
			for(PropertyValue propertyValue: fieldDefinitionList)
			{
				BeanUtil.invokeSetterMethod(bean, propertyValue.getName(), propertyValue.getValue());
			}
						
			beanDefinition.setBean(bean);
			
			return beanDefinition;
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
