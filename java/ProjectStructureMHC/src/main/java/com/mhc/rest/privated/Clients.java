package com.mhc.rest.privated;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mhc.dao.ClientsDAO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.rest.BaseRest;

@Path("clients")
@Produces("application/json")
public class Clients extends BaseRest{

	ClientsDAO clientDAO;
	
	@GET
	public GenericResponse getClients() {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		response.getMeta().setMsg("");
		clientDAO = (ClientsDAO) beanFactory.getBean("clientsDAO");
		List<ClientDTO> clients = clientDAO.getClients();
		response.setResponse(clients);
		return response;
	}

}
