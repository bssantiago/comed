package com.mhc.rest.privated;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mhc.dao.ReportDAO;
import com.mhc.dto.GenericResponse;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.rest.BaseRest;

@Path("reports")
@Produces("application/json")
public class ReportInfo extends BaseRest {
	
	private ReportDAO reoprtDAO = (ReportDAO) beanFactory.getBean("reoprtDAO");
	
	@POST
	@Path("blood")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse bloodReport(){
		try {			
			GenericResponse response = new GenericResponse("success",0,reoprtDAO.bloodReport());			
			return response;			
		}catch (Exception se) {
			return new GenericResponse("Error", -1);
		} 
	}
	
	@POST
	@Path("health")
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse healthOverviewReport(HealthOverviewDTO dto){
		try {			
			GenericResponse response = new GenericResponse("success",0,reoprtDAO.healthOverviewReport(dto.participant_id));			
			return response;			
		}catch (Exception se) {
			return new GenericResponse(se.getMessage(), -1);
		} 
	}
	
	
	
	
	

}
