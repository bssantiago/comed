package com.mhc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;

@Path("authenticate")
@Produces("application/json")
public class Authentication {

	@POST
	public Response authenticate() {
		
		
		return null;
	}

}
