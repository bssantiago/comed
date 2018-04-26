package com.mhc.rest.privated;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mhc.dao.ReportDAO;
import com.mhc.dto.GenericResponse;
import com.mhc.rest.BaseRest;

@Path("reports")
@Produces("application/json")
public class ReportInfo extends BaseRest {
	
	private ReportDAO reoprtDAO = (ReportDAO) beanFactory.getBean("reoprtDAO");
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse bloodReport(){
		try {			
			GenericResponse response = new GenericResponse("success",0,reoprtDAO.bloodReport());			
			return response;			
		}catch (Exception se) {
			return new GenericResponse("Error", -1);
		} 
	}

}
