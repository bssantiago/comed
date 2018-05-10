package com.mhc.rest.privated;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.mhc.dao.ReportDAO;
import com.mhc.dto.GenericResponse;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.rest.BaseRest;
import com.mhc.util.Constants;

@Path("reports")
@Produces("application/json")
public class ReportInfo extends BaseRest {
	
	private ReportDAO reoprtDAO = (ReportDAO) beanFactory.getBean("reoprtDAO");
	private static final Logger LOG = Logger.getLogger(ReportInfo.class);
	private MessageSource messageSource = (MessageSource) beanFactory.getBean("messageSource");
	
	@POST
	@Path("blood")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse bloodReport(){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.bloodReport());			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("health")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse healthOverviewReport(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.healthOverviewReport(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("history/blood")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse historyBlood(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.bloodHistory(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("history/cholesterol")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse historyCholesterol(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.cholesterolHistory(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("history/hdl")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse historyHdl(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.hdlHistory(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("history/ldl")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse historyLdl(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.ldlHistory(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("history/triglycerides")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse historyTriglycerides(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.triglyceridesHistory(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	@POST
	@Path("history/waist")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse historyWaist(HealthOverviewDTO dto){
		try {
			GenericResponse response = new GenericResponse(StringUtils.EMPTY,0,reoprtDAO.waistHistory(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			LOG.error(se);
			return new GenericResponse(messageSource.getMessage(Constants.ERROR_SERVER, null, null), -1);
		} 
	}
	
	
	
	

}
