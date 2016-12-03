package dev.aop.framework;

import dev.resource.Resource;

public class ProxyFactory extends AdvisedSupport{
	
	public Object getProxy(String proxyId) {
		return new JdkDynamicAopProxy(this).getProxy();
	}
	
	public Object getProxy() {
		return new JdkDynamicAopProxy(this).getProxy();
	}
}
