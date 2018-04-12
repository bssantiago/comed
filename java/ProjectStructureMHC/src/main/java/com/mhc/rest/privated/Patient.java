package com.mhc.rest.privated;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.mhc.rest.BaseRest;
import com.mhc.util.Constants;
import com.mhc.util.InitUtil;
import com.sun.jersey.api.NotFoundException;

@Path("patients")
@Produces("application/json")
public class Patient extends BaseRest{

	@GET
	public String getPatients(@Context HttpServletRequest request) {
		System.out.println("patient: " +request.getSession().getAttribute(Constants.HEADER_PATIENT_ID));
		System.out.println("client: " +request.getSession().getAttribute(Constants.HEADER_CLIENT_ID));
		System.out.println("request-by: " +request.getSession().getAttribute(Constants.HEADER_REQUEST_BY));
		return "cuevas";
	}

	@GET
	@Path("/{id}")
	public String getPatient(@PathParam("id") String id) throws NotFoundException {
		return "cuevitas2";
	}

}
