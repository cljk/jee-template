package de.csmp.jeetemplate.web.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {
	@Context
    ResourceContext rc;
	
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "say : " + msg;
		return Response.status(200).entity(output).build();
	}
}
