package de.csmp.jeetemplate.ejbs.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.csmp.jeetemplate.common.logging.Logged;
import de.csmp.jeetemplate.ejbs.service.TestServiceLocal;

@Logged	// FIXME - need EJB interceptor
@Stateless
public class TestServiceBean implements TestServiceLocal {
	//@Inject
	private Logger log;
	
	/* (non-Javadoc)
	 * @see de.csmp.jeetemplate.ejbs.service.TestServiceLocal#doNothing()
	 */
	@Override
	public void doNothing() {
		// just get logged
		//log.info("do nothing");
		System.out.println("ejb do nothing");
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
