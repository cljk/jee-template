package de.csmp.jeetemplate.web;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
public class WebApplication implements Serializable {

	@Inject
	private Logger log;
	
	
	@Inject
	private ServletContext context;
	
	private Date startupDate = null;
	
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        // just need to observe
    }
 
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        // just need to observe
    }
	
	@PostConstruct
	public void postConstruct() {
		startupDate = new Date();
		
		log.info(StringUtils.repeat("=", 80));
		log.info("startup at " + startupDate);
		log.info("");
		log.info(StringUtils.repeat(" ", 60) + "application at: " + context.getContextPath());
		log.info("");
		log.info(StringUtils.repeat("=", 80));
		
		// init log4j
		org.apache.logging.log4j.spi.LoggerContext lctx = org.apache.logging.log4j.LogManager.getContext();;
	}
	
	@PreDestroy
	public void shutdown() {
		log.info(StringUtils.repeat("=", 80));
		log.info("startup was at " + startupDate);
		log.info("");
		log.info("shutdown ... at " + (new Date()));
		log.info(StringUtils.repeat("=", 80));
	}

	public Date getStartupDate() {
		return startupDate;
	}

	private void setStartupDate(Date startupDate) {
		this.startupDate = startupDate;
	}

}
