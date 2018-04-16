package com.mhc.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("authenticate")
@Produces("application/json")
public class Authentication {

	
	
	@GET
	public Response authenticateGet(@Context HttpServletRequest request) {		
		return null;
	}
	
	@POST
	public Response authenticatePost(@Context HttpServletRequest request) {		
		return null;
	}

}
