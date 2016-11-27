package test;

import dev.factory.BeanFactory;
import dev.factory.XMLBeanFactory;
import dev.resource.LocalFileResource;

public class test {

    public static void main(String[] args) throws ClassNotFoundException {
        //String[] locations = {"bean.xml"};
        //ApplicationContext ctx = 
		//    new ClassPathXmlApplicationContext(locations);
    	LocalFileResource resource = new LocalFileResource("bean.xml");	
		BeanFactory beanFactory = new XMLBeanFactory(resource);
    	boss boss = (boss) beanFactory.getBean("boss");
        System.out.println(boss.toString()); 
    }
}