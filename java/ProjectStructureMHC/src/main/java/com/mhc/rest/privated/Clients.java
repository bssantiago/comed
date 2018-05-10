package com.mhc.rest.privated;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;

import com.mhc.dao.ClientsDAO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.rest.BaseRest;

@Path("clients")
@Produces("application/json")
public class Clients extends BaseRest {

	private ClientsDAO clientDAO;

	@GET
	public GenericResponse getClients() {
		clientDAO = (ClientsDAO) beanFactory.getBean("clientsDAO");
		List<ClientDTO> clients = clientDAO.getClients();
		return new GenericResponse(StringUtils.EMPTY, 0, clients);
	}

}
