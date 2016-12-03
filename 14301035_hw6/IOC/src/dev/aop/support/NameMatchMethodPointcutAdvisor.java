package dev.aop.support;

import dev.aop.ClassFilter;
import dev.aop.Pointcut;

public class NameMatchMethodPointcutAdvisor extends AbstractPointcutAdvisor{

	private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	public void setClassFilter(ClassFilter classFilter) {
		this.pointcut.setClassFilter(classFilter);
	}
	
	public void setMappedName(String mappedName) {
		this.pointcut.setMappedName(mappedName);
	}
	
	public void setMappedNames(String... mappedNames) {
		this.pointcut.setMappedNames(mappedNames);
	}
}
