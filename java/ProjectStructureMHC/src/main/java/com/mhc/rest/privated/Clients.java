package com.mhc.rest.privated;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mhc.dao.ClientsDAO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.rest.BaseRest;
import com.sun.jersey.api.NotFoundException;

@Path("clients")
@Produces("application/json")
public class Clients extends BaseRest{

	ClientsDAO clientDAO;
	
	@GET
	public GenericResponse getPatients() {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		response.getMeta().setMsg("");
		clientDAO = (ClientsDAO) beanFactory.getBean("clientsDAO");
		List<ClientDTO> clients = clientDAO.getClients();
		response.setResponse(clients);
		return response;
	}

	@GET
	@Path("/{id}")
	public String getPatient(@PathParam("id") String id) throws NotFoundException {
		return "cuevitas2";
	}

}
