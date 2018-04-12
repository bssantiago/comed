package com.mhc.rest.privated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.dao.EmptyResultDataAccessException;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.rest.BaseRest;
import com.sun.jersey.api.NotFoundException;

import com.mhc.dao.BiometricInfoDAO;

@Path("biometrics")
@Produces("application/json")
public class BiometricInfo extends BaseRest {

	private BiometricInfoDAO biometricInfoDAO = (BiometricInfoDAO) beanFactory.getBean("biometricInfoDAO");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse setBiometrics(BiometricInfoDTO bio) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		try {
			this.biometricInfoDAO.saveBiometricInfo(bio);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(e.getMessage(), -1);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse updateBiometrics(BiometricInfoDTO bio) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		try {
			this.biometricInfoDAO.updateBiometricInfo(bio);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(e.getMessage(), -1);
		}
	}

	@GET
	@Path("/{id}")
	public GenericResponse getBiometricInfo(@PathParam("id") Integer id) {
		try {
			BiometricInfoDTO binfo = this.biometricInfoDAO.getBiometricInfo(id);
			GenericResponse res = new GenericResponse("", 0, binfo);
			return res;
		}
		catch(EmptyResultDataAccessException ex) {
			return new GenericResponse("No records found for this participant", -1);
		}
		catch (Exception e) {			
			return new GenericResponse(e.getMessage(), -1);
		}

	}

}
