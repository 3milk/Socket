package dev.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dev.aop.AfterReturningAdvice;
import dev.aop.AopProxy;
import dev.aop.MethodBeforeAdvice;
import dev.aop.MethodInterceptor;
import dev.aop.ThrowsAdvice;
import dev.aop.support.AfterReturningAdviceInterceptor;
import dev.aop.support.MethodBeforeAdviceInterceptor;
import dev.aop.support.PointcutAdvisor;
import dev.aop.support.ThrowsAdviceInterceptor;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler{

	private AdvisedSupport advised;
	
	public JdkDynamicAopProxy(AdvisedSupport advised)
	{
		this.advised = advised;
	}
	
	public Object getProxy()
	{
		return Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class[]{advised.getInterfaces()}, 
				this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advised.getAdvisor();
		
		if(pointcutAdvisor.getPointcut().getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass()))
		{
			
			if(advised.getAdvisor().getAdvice() instanceof MethodBeforeAdvice)
			{
				MethodBeforeAdvice advice = (MethodBeforeAdvice)advised.getAdvisor().getAdvice();
				MethodInterceptor interceptor = new MethodBeforeAdviceInterceptor(advice);
				return interceptor.invoke(advised.getTargetSource().getTarget(), method, args);
			}
			if(advised.getAdvisor().getAdvice() instanceof AfterReturningAdvice)
			{
				AfterReturningAdvice advice = (AfterReturningAdvice)advised.getAdvisor().getAdvice();
				MethodInterceptor interceptor = new AfterReturningAdviceInterceptor(advice);
				return interceptor.invoke(advised.getTargetSource().getTarget(), method, args);
			}
			if(advised.getAdvisor().getAdvice() instanceof ThrowsAdvice)
			{
				ThrowsAdvice advice = (ThrowsAdvice)advised.getAdvisor().getAdvice();
				MethodInterceptor interceptor = new ThrowsAdviceInterceptor(advice);
				return interceptor.invoke(advised.getTargetSource().getTarget(), method, args);
			}
		}
			
		return method.invoke(advised.getTargetSource().getTarget(), args);
	}

}
