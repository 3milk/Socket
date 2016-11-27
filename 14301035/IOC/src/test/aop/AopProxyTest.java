package test.aop;

//import org.junit.Test;
import dev.resource.LocalFileResource;
import dev.aop.framework.ProxyFactory;
import dev.aop.support.JdkRegexpMethodPointcutAdvisor;
import dev.aop.support.NameMatchMethodPointcutAdvisor;
import dev.aop.support.TargetSource;
import dev.factory.BeanFactory;
import dev.factory.XMLBeanFactory;

public class AopProxyTest {
	public static void main(String[] args) throws ClassNotFoundException {
        LocalFileResource resource = new LocalFileResource("AOP.xml");
		BeanFactory beanFactory = new XMLBeanFactory(resource);
	    FooInterface foo = (FooInterface)beanFactory.getBean("foo");
	    foo.printFoo();
	    foo.dummyFoo();
	  }
	/*
	@Test
	public void TestNameMatchMethodPointcut()
	{
		ISubject realSubject = new RealSubject();
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(realSubject);
		proxyFactory.setInterfaces(ISubject.class);
		NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
		advisor.setAdvice(new SimpleLogBeforeMehtod());
		advisor.setMappedName("printSecondMessage");
		proxyFactory.setAdvisor(advisor);
		
		ISubject subjectProy = (ISubject) proxyFactory.getProxy();
		
		subjectProy.printFirstMessage();
		subjectProy.printSecondMessage();
	}
	
	@Test
	public void TestJdkRegexpMethodPointcut()
	{
		ISubject realSubject = new RealSubject();
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(realSubject);
		proxyFactory.setInterfaces(ISubject.class);
		JdkRegexpMethodPointcutAdvisor advisor = new JdkRegexpMethodPointcutAdvisor();
		advisor.setAdvice(new SimpleLogAfterReturningAdvice());
		advisor.setPattern("printSecond\\w+");
		proxyFactory.setAdvisor(advisor);
		
		ISubject subjectProy = (ISubject) proxyFactory.getProxy();
		
		subjectProy.printFirstMessage();
		subjectProy.printSecondMessage();
	}*/
}
