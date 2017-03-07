package de.csmp.jeetemplate.web.rs;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.logging.log4j.core.Logger;


@ApplicationPath("rest")
public class RestApplication extends Application {

	@Inject
	Logger log;
	
	@PostConstruct
	public void init() {
		log.info("init");
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestService.class);
		return classes;
	}
}
