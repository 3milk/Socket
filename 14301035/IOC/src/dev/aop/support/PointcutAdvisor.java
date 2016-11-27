package dev.aop.support;

import dev.aop.Advisor;
import dev.aop.Pointcut;

public interface PointcutAdvisor extends Advisor {
	Pointcut getPointcut();
}
