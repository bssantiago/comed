package com.mhc.rest.privated;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;

import com.mhc.dao.ClientAssesmentDAO;
import com.mhc.dao.ClientsDAO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.rest.BaseRest;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("client_assessment")
@Produces("application/json")
public class ClientAssessment extends BaseRest {

	private ClientAssesmentDAO clientAssesmentDAO;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse setClientAssessment(ClientAssessmentDTO clientAssessment) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		// check if all form parameters are provided
		if (clientAssessment.getFile() == null) {
			response.getMeta().setErrCode(-1);
			response.getMeta().setMsg("Invalid form data");
			return response;

		} else {
			try {
				createFolderIfNotExists("c:/uploadedFiles/");
				String[] base64File = clientAssessment.getFile().split(";");
				String file = null;
				for (String index : base64File) {
					if (index.contains("base64")) {
						file = index.replaceAll("base64,", "");
						break;
					}
				}
				byte[] byteArray = Base64.decodeBase64(file.getBytes());
				System.out.println(file);
				InputStream uploadedInputStream = new ByteArrayInputStream(byteArray);
				String uploadedFileLocation = "c:/uploadedFiles/algo.csv";
				saveToFile(uploadedInputStream, uploadedFileLocation);
			} catch (SecurityException se) {
				response.getMeta().setErrCode(-1);
				response.getMeta().setMsg("Can not create destination folder on server");
			} catch (IOException e) {
				response.getMeta().setErrCode(-1);
				response.getMeta().setMsg("Can not create destination folder on server");
			}
		}

		return response;
	}

	@GET
	public GenericResponse getClientAssessments() {
		this.clientAssesmentDAO = (ClientAssesmentDAO) beanFactory.getBean("clientAssesmentDAO");
		List<ClientAssessmentDTO> clientAssesmentList = this.clientAssesmentDAO.getClientsAssesments();
		GenericResponse res = new GenericResponse("", 0, clientAssesmentList);
		return res;
	}

	/**
	 * Utility method to save InputStream data to target location/file
	 * 
	 * @param inStream
	 *            - InputStream to be saved
	 * @param target
	 *            - full path to destination file
	 */
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

	/**
	 * Creates a folder to desired location if it not already exists
	 * 
	 * @param dirName
	 *            - full path to the folder
	 * @throws SecurityException
	 *             - in case you don't have permission to create the folder
	 */
	private void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
}
