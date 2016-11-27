package dev.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
	boolean matches(Method method, Class<?> targetClass);
	boolean mathces(Method method, Class<?> targetClass, Object... args);
	boolean isRuntime();
}
