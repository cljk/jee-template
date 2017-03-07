package de.csmp.jeetemplate.web.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.csmp.jeetemplate.common.configuration.ConfigProperty;
import de.csmp.jeetemplate.ejbs.service.TestServiceLocal;

@Named
@WebServlet(urlPatterns={"/test"})
public class TestServlet extends HttpServlet implements Serializable {
	private static final long serialVersionUID = -385682641759861234L;
	
	@Inject
	Logger log;
	
	@EJB
	TestServiceLocal testService;
	
	@Inject
	@ConfigProperty(key="test", defaultValue="missing")
	String test;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("doGet");
		ServletOutputStream os = resp.getOutputStream();
		os.print("test response: " + test + " --- ");
		testService.doNothing();
	}

	
	

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public TestServiceLocal getTestService() {
		return testService;
	}

	public void setTestService(TestServiceLocal testService) {
		this.testService = testService;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
