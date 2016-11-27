package dev.aop.support;

import java.lang.reflect.Method;

import dev.aop.AfterReturningAdvice;
import dev.aop.MethodInterceptor;

public class AfterReturningAdviceInterceptor implements MethodInterceptor {

	private AfterReturningAdvice advice;
	
	public AfterReturningAdviceInterceptor(AfterReturningAdvice advice)
	{
		this.advice = advice;
	}
	
	@Override
	public Object invoke(Object target, Method method, Object[] args) throws Throwable {
		Object returnValue = method.invoke(target, args);
		advice.afterReturning(returnValue, method, args, target);
		return returnValue;
	}

}
