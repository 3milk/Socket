package dev.aop.support;

import dev.aop.Advice;

public abstract class AbstractPointcutAdvisor implements PointcutAdvisor {

	private Advice advice;
	
	public void setAdvice(Advice advice) {
		this.advice = advice;
	}
	@Override
	public Advice getAdvice() {
		// TODO Auto-generated method stub
		return advice;
	}
}
