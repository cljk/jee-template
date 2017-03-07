package de.csmp.jeetemplate.common.logging;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;


@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
	private static final long serialVersionUID = -4815116567890282249L;

	@Inject
	private transient Logger log;
	
	@AroundInvoke
	public Object logInvocation(InvocationContext ctx) throws Exception {
		log.info(">> " + ctx.getClass() + "." + ctx.getMethod());
		System.out.println("logInvocation");
		try {
			Object res = ctx.proceed();
			return res;
		} finally {
			log.info("<< " + ctx.getClass() + "." + ctx.getMethod());
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
