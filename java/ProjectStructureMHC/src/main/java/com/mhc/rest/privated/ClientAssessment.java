package com.mhc.rest.privated;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import com.mhc.dao.ClientAssesmentDAO;
import com.mhc.dao.ClientsDAO;
import com.mhc.dao.ParticipantDAO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.dto.GenericSearchDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.exceptions.ParseCSVException;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.rest.BaseRest;
import com.mhc.util.CSVUtil;
import com.mhc.util.Constants;
import com.sun.jersey.api.NotFoundException;

@Path("client_assessment")
@Produces("application/json")
public class ClientAssessment extends BaseRest {
	private ClientsDAO clientsDAO = (ClientsDAO) beanFactory.getBean("clientsDAO");
	private ClientAssesmentDAO clientAssesmentDAO = (ClientAssesmentDAO) beanFactory.getBean("clientAssesmentDAO");
	private MessageSource messageSource = (MessageSource) beanFactory.getBean("messageSource");
	private ParticipantDAO participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
	private static final Logger LOG = Logger.getLogger(BiometricInfo.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public GenericResponse setClientAssessment(ClientAssessmentDTO clientAssessment) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		// check if all form parameters are provided
		if (clientAssessment.getFile() == null) {
			return new GenericResponse("Invalid form data", -1);
		} else {
			try {
				String fileSystemPath = messageSource.getMessage(Constants.CSV_FILE_PATH, null, null);
				createFolderIfNotExists(fileSystemPath);
				String[] base64File = clientAssessment.getFile().split(";");
				String file = null;
				for (String index : base64File) {
					if (index.contains("base64")) {
						file = index.replaceAll("base64,", "");
						break;
					}
				}
				byte[] byteArray = Base64.decodeBase64(file.getBytes());				
				InputStream uploadedInputStream = new ByteArrayInputStream(byteArray);
				ClientDTO client = clientsDAO.getClient(clientAssessment.getClient_id());
				List<ParticipantsDTO> participants = CSVUtil.csvToParticipants(clientAssessment.getClient_id(), client.getHighmark_client_id(), uploadedInputStream);
				if (participants.isEmpty()) {
					return new GenericResponse(messageSource.getMessage(Constants.ERROR_NO_PATIENT_WHERE_SAVED, null, null), -1);	
				}				
				SimpleDateFormat sdfr = new SimpleDateFormat(Constants.DATE_FORMAT);				
				clientAssessment.setProgram_display_name(clientAssessment.getProgram_id() + " - " + sdfr.format(clientAssessment.getReward_date()));
				participantDAO.setParticipantBatch(participants, clientAssessment);	
				String uploadedFileLocation = fileSystemPath + clientAssessment.getFile_name();
				saveToFile(uploadedInputStream, uploadedFileLocation);
			} catch (ParseCSVException p) {
				LOG.error(p);
				return new GenericResponse(p.getMessage(), -1);
			} catch (SecurityException se) {
				LOG.error(se);
				return new GenericResponse(messageSource.getMessage(Constants.ERROR_CSV_SAVE, null, null), -1);
			} catch (IOException e) {
				LOG.error(e);
				return new GenericResponse(messageSource.getMessage(Constants.ERROR_CSV_SAVE, null, null), -1);
			} catch (ParseException e) {
				LOG.error(e);
				return new GenericResponse(messageSource.getMessage(Constants.ERROR_INVALID_DATA, null, null), -1);
			} catch (DAOSystemException ex) {
				return new GenericResponse(ex.getMessage(), -1);
			}
		}

		return response;
	}

	@POST
	@Path("search")
	public GenericResponse getClientAssessments(GenericSearchDTO search) {		
		SearchResultDTO<ClientAssessmentDTO> clientAssesmentList = this.clientAssesmentDAO.getClientsAssesments(search);
		GenericResponse res = new GenericResponse("", 0, clientAssesmentList);
		return res;
	}

	private void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	private void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
}
