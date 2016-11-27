package test.aop;

import java.lang.reflect.Method;

import dev.aop.MethodBeforeAdvice;

public class PrintBeforeAdvice implements MethodBeforeAdvice{

	public PrintBeforeAdvice() {
		// TODO Auto-generated constructor stub
		System.out.println("print before advice: JAVAEE");

	}

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("print before advice: JAVAEE");
	}

}
