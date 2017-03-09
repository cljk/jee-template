package de.csmp.jeetemplate.web;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.lang3.StringUtils;

import de.csmp.jeetemplate.web.configuration.ConfigurationUtil;

@ApplicationScoped
public class WebApplication implements Serializable {

	@Inject
	private Logger log;
	
	
	@Inject
	private ServletContext context;
	
	private Date startupDate = null;
	private String runHash = "notInitialized";
	
	@Produces
	CompositeConfiguration configuration = null;
	
	
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        // just need to observe
    }
 
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        // just need to observe
    }
	
	@PostConstruct
	public void postConstruct() {
		startupDate = new Date();
		runHash = UUID.randomUUID().toString().substring(0, 8);
		
		configuration = ConfigurationUtil.initConfiguration(null);
		
		log.info(StringUtils.repeat("=", 80));
		log.info("startup at " + startupDate);
		log.info("");
		log.info(StringUtils.repeat(" ", 40) + "application at: " + context.getContextPath());
		log.info(StringUtils.repeat(" ", 40) + "runHash:        " + runHash);
		log.info(StringUtils.repeat(" ", 40) + "environment:    " + configuration.getString("environment"));
		log.info("");
		log.info(StringUtils.repeat("=", 80));
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
