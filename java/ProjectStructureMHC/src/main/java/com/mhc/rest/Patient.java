package com.mhc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sun.jersey.api.NotFoundException;

@Path("patients")
@Produces("application/json")
public class Patient {

	@GET
	public String getPatients() {
		return "cuevas";
	}

	@GET
	@Path("/{id}")
	public String getPatient(@PathParam("id") String id) throws NotFoundException {
		return "cuevitas2";
	}

}
