package com.mhc.rest.privated;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mhc.dao.BiometricInfoDAO;
import com.mhc.dao.ParticipantDAO;
import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.dto.GenericSearchDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.rest.BaseRest;
import com.sun.jersey.api.NotFoundException;
import java.text.NumberFormat;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("participant")

public class Participant extends BaseRest {
	private ParticipantDAO participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
	private BiometricInfoDAO biometricInfoDAO = (BiometricInfoDAO) beanFactory.getBean("biometricInfoDAO");
	
	
	@GET
	@Path("firstnames/{firstname}")
	@Produces("application/json")
	public GenericResponse getFirstNames(@PathParam("firstname") String firstname) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		response.getMeta().setMsg("");
		
		List<String> firstnames = this.participantDAO.getFirstNames(firstname);
		response.setResponse(firstnames);
		return response;
	}

	@GET
	@Path("lastnames/{lastname}")
	@Produces("application/json")
	public GenericResponse getLastName(@PathParam("lastname") String lastname) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		response.getMeta().setMsg("");
		
		List<String> lastnames = this.participantDAO.getLastNames(lastname);
		response.setResponse(lastnames);
		return response;
	}
	
	@POST
	@Path("bindParticipantClient")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse search(ParticipantsDTO request) throws NotFoundException {
		GenericResponse response = new GenericResponse();		
		try {
			this.participantDAO.bindParticipantWithClient(request);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(e.getMessage(), -1);
		}
	}
	

	@POST
	@Path("search")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse search(SearchDTO request) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		response.getMeta().setErrCode(0);
		
		SearchResultDTO result = this.participantDAO.search(request);
		response.setResponse(result);
		return response;
	}

	@GET
	@Path("file")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getTxt(@QueryParam("client_id") Integer client_id,@QueryParam("program_id") String program_id) throws NotFoundException, IOException {
		
		
		File result = this.participantDAO.getTxt(client_id,program_id);
		return download(result);
		
	}

	private Response download(File file) {
		Response response = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);

		// Retrieve the file
		if (file.exists()) {
			ResponseBuilder builder = Response.ok(file);
			builder.header("Content-Disposition", "attachment; filename=" + file.getName());
			response = builder.build();
		} else {

			response = Response.status(404).entity("FILE NOT FOUND: ").type("text/plain").build();
		}

		return response;
	}
	
	@GET
	@Path("pdf")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getPdf(@QueryParam("participant_id") Integer participant_id) throws NotFoundException, IOException {
		System.out.println(System.getProperty("catalina.base"));
		BiometricInfoDTO binfo = this.biometricInfoDAO.getBiometricInfo(participant_id);
		
		File result = this.participantDAO.getPdf(binfo);
		return downloadPdf(result);		
	}
	
	private Response downloadPdf(File file) {
		Response response = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);

		if (file.exists()) {
			ResponseBuilder builder = Response.ok(file);
			builder.header("Content-Disposition", "attachment; filename=" + file.getName());
			response = builder.build();
		} else {
			response = Response.status(404).entity("FILE NOT FOUND: ").type("application/pdf").build();
		}

		return response;
	}

}
