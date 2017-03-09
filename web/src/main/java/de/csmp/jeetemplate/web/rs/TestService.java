package de.csmp.jeetemplate.web.rs;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.csmp.jeetemplate.common.logging.Logged;

@Logged
@Path("/test")
public class TestService {
	@Context
    ResourceContext rc;
	
	@Inject
	Logger log;
	
	@Logged
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		log.info("call rest");
		String output = "say : " + msg;
		return Response.status(200).entity(output).build();
	}
}
