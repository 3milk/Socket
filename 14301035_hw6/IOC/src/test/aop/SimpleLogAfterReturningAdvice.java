package test.aop;

import java.lang.reflect.Method;

import dev.aop.AfterReturningAdvice;

public class SimpleLogAfterReturningAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("The Log Finish");
	}

}
