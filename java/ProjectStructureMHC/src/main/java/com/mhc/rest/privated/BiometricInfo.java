package com.mhc.rest.privated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;

import com.mhc.dao.BiometricInfoDAO;
import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.GenericResponse;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.rest.BaseRest;
import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;
import com.sun.jersey.api.NotFoundException;

@Path("biometrics")
@Produces("application/json")
public class BiometricInfo extends BaseRest {

	private BiometricInfoDAO biometricInfoDAO = (BiometricInfoDAO) beanFactory.getBean("biometricInfoDAO");
	private static MessageSource messageSource = (MessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");
	private static final Logger LOG = Logger.getLogger(BiometricInfo.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse setBiometrics(BiometricInfoDTO bio) throws NotFoundException {
		GenericResponse response = new GenericResponse();
		try {
			this.biometricInfoDAO.saveBiometricInfo(bio);
			return response;
		} catch (DAOSystemException dse) {
			LOG.error(dse);
			return new GenericResponse(dse.getMessage(), -1);
		} catch (Exception e) {
			LOG.error(e);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
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
			LOG.error(e);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
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
			LOG.error(ex);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_NOT_BIOMETRIC_INFO, null, null), -1);
		}
		catch (Exception e) {
			LOG.error(e);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		}

	}

}
