package com.mhc.rest.privated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mhc.rest.BaseRest;
import com.mhc.util.InitUtil;
import com.sun.jersey.api.NotFoundException;

@Path("patients")
@Produces("application/json")
public class Patient extends BaseRest{

	@GET
	public String getPatients() {
		System.out.println("SALT: " + InitUtil.getSalt());
		return "cuevas";
	}

	@GET
	@Path("/{id}")
	public String getPatient(@PathParam("id") String id) throws NotFoundException {
		return "cuevitas2";
	}

}
