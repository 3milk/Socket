package dev.aop;

import java.lang.reflect.Method;

public interface MethodInterceptor {
	Object invoke(Object target,Method method,Object[] args) throws Throwable;
}
