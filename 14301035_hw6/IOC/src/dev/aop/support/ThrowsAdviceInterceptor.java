package dev.aop.support;

import java.lang.reflect.Method;

import dev.aop.MethodInterceptor;
import dev.aop.ThrowsAdvice;

public class ThrowsAdviceInterceptor implements MethodInterceptor {

	private ThrowsAdvice advice;
	
	public ThrowsAdviceInterceptor(ThrowsAdvice advice)
	{
		this.advice = advice;
	}
	
	@Override
	public Object invoke(Object target, Method method, Object[] args) throws Throwable {
		try {
			Object returnValue = method.invoke(target, args);
			return returnValue;
		}
		catch (Throwable ex) {
			//We didn`t define our exceptionHandler for special exceptionClass, just simply throw the exception.
			throw ex;
		}
	}

}
