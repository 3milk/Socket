package test.aop;

import java.lang.reflect.Method;

import dev.aop.MethodBeforeAdvice;

public class SimpleLogBeforeMehtod implements MethodBeforeAdvice{

	@Override
	public void before(Method method, Object[] args, Object target) {
		System.out.println("The Log Start");
	}

}
