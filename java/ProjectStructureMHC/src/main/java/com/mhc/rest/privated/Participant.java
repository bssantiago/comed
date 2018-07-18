package com.mhc.rest.privated;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.mhc.dao.BiometricInfoDAO;
import com.mhc.dao.ClientsDAO;
import com.mhc.dao.HealthReportDAO;
import com.mhc.dao.ParticipantDAO;
import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.rest.BaseRest;
import com.mhc.util.Constants;
import com.sun.jersey.api.NotFoundException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Path("participant")

public class Participant extends BaseRest {
	private ParticipantDAO participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
	private ClientsDAO clientDAO = (ClientsDAO) beanFactory.getBean("clientsDAO");
	private BiometricInfoDAO biometricInfoDAO = (BiometricInfoDAO) beanFactory.getBean("biometricInfoDAO");
	private MessageSource messageSource = (MessageSource) beanFactory.getBean("messageSource");
	private HealthReportDAO reportDAO = (HealthReportDAO) beanFactory.getBean("healthReportDAO");
	private static final Logger LOG = Logger.getLogger(Participant.class);

	@GET
	@Path("firstnames/{client_id}/{firstname}")
	@Produces("application/json")
	public GenericResponse getFirstNames(@PathParam("firstname") String firstname, @PathParam("client_id") int client)
			throws NotFoundException {
		List<String> firstnames = this.participantDAO.getFirstNames(firstname, client);
		return new GenericResponse(StringUtils.EMPTY, 0, firstnames);
	}

	@GET
	@Path("lastnames/{client_id}/{lastname}")
	@Produces("application/json")
	public GenericResponse getLastName(@PathParam("lastname") String lastname, @PathParam("client_id") int client)
			throws NotFoundException {
		List<String> lastnames = this.participantDAO.getLastNames(lastname, client);
		return new GenericResponse(StringUtils.EMPTY, 0, lastnames);
	}

	@POST
	@Path("setParticipant")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse setParticipant(ParticipantsDTO request) throws NotFoundException {
		try {
			return new GenericResponse(StringUtils.EMPTY, 0, this.participantDAO.setParticipant(request));
		} catch (Exception e) {
			LOG.error(e);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		}
	}

	@POST
	@Path("bindParticipantClient")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse search(ParticipantsDTO request) throws NotFoundException {
		try {
			if (this.participantDAO.bindParticipantWithClient(request) == 0)
				return new GenericResponse(messageSource.getMessage(Constants.WARNING_PATIENT_ALREADY_BIND, null, null),
						0);
			return new GenericResponse(StringUtils.EMPTY, 0);
		} catch (Exception e) {
			LOG.error(e);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		}
	}

	@POST
	@Path("search")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	@SuppressWarnings("rawtypes")
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
	public Response getTxt(@QueryParam("client_id") Integer client_id, @QueryParam("program_id") String program_id,
			@QueryParam("mark_download") Boolean markDownload) throws NotFoundException, IOException {
		ClientDTO dto = clientDAO.getClient(client_id);
		File result = null;

		result = this.participantDAO.getTxt(program_id, dto);
		if (markDownload) {
			this.participantDAO.setDownloadedBiometricInfo(client_id, program_id);
		}
		return download(result);

	}

	private Response download(File file) {
		Response response = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		try {
			// Retrieve the file
			if (file.exists()) {
				ResponseBuilder builder = Response.ok(FileUtils.readFileToByteArray(file));
				builder.header("Content-Disposition", "attachment; filename=" + file.getName());
				response = builder.build();
			} else {
				response = Response.status(404).entity("FILE NOT FOUND: ").type("text/plain").build();
			}
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			if (file != null) {
				file.delete();
			}
		}
		return response;
	}

	@GET
	@Path("pdf")
	@Produces("application/pdf")
	public Response getPdf(@QueryParam("participant_id") Integer participant_id) throws NotFoundException, IOException {
		BiometricInfoDTO binfo = this.biometricInfoDAO.getBiometricInfo(participant_id);
		File result = this.participantDAO.getPdf(binfo);
		return downloadPdf(result);
	}

	@GET
	@Path("helath_report")
	@Produces("application/pdf")
	public Response getHealthReport(@QueryParam("participant_id") Integer participant_id)
			throws NotFoundException, IOException {
		BiometricInfoDTO binfo = this.biometricInfoDAO.getBiometricInfo(participant_id);
		OutputStream fileOutput = null;
		File file = null;
		try {
			JasperPrint print = reportDAO.getReport(binfo);
			file = new File(messageSource.getMessage(Constants.TMP_FOLDER, null, null) + participant_id
					+ "report_overview.pdf");
			fileOutput = new FileOutputStream(file);
			JasperExportManager.exportReportToPdfStream(print, fileOutput);
			ResponseBuilder builder = Response.ok(FileUtils.readFileToByteArray(file));
			builder.header("Content-Disposition", "filename=Health_Letter.pdf");
			Response response = builder.type("application/pdf").build();
			return response;
		} catch (JRException e) {
			LOG.error(e);
		} catch (FileNotFoundException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		} catch (SQLException e) {
			LOG.error(e);
		} finally {
			try {
				if (fileOutput != null) {
					fileOutput.close();
				}
				if (file != null) {
					file.delete();
				}
			} catch (Exception e) {
				LOG.error(e);
			}

		}
		return null;

	}

	private Response downloadPdf(File file) throws FileNotFoundException {
		Response response = null;
		try {
			if (file.exists()) {
				ResponseBuilder builder = Response.ok().entity(FileUtils.readFileToByteArray(file));
				builder.header("Content-Disposition", "filename=Doctor_Letter.pdf");
				response = builder.build();
			} else {
				response = Response.status(404).entity("FILE NOT FOUND: ").type("application/pdf").build();
			}
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			if (file != null) {
				file.delete();
			}
		}
		return response;
	}

}
