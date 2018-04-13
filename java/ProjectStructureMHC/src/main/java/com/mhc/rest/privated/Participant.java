package com.mhc.rest.privated;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mhc.dao.ClientsDAO;
import com.mhc.dao.ParticipantDAO;
import com.mhc.dto.BaseParticipantDTO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.dto.LigthParticipantDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.rest.BaseRest;
import com.sun.jersey.api.NotFoundException;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Path("participant")
@Produces("application/json")
public class Participant extends BaseRest {

	ParticipantDAO participantDAO;
	
	@GET
	@Path("firstnames/{firstname}")
	public GenericResponse getFirstNames(@PathParam("firstname") String firstname) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		response.getMeta().setMsg("");
		participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
		List<String> firstnames = participantDAO.getFirstNames(firstname);
		response.setResponse(firstnames);
		return response;
	}
	
	@GET
	@Path("lastnames/{lastname}")
	public GenericResponse getLastName(@PathParam("lastname") String lastname) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		response.getMeta().setMsg("");
		participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
		List<String> lastnames = participantDAO.getLastNames(lastname);
		response.setResponse(lastnames);
		return response;
	}
	
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse search(SearchDTO request) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
		SearchResultDTO result = participantDAO.search(request);
		response.setResponse(result);
		return response;
	}
	
}
