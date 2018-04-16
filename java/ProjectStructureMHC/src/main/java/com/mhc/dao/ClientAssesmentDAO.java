package com.mhc.dao;

import java.util.List;

import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.GenericSearchDTO;

public interface ClientAssesmentDAO {
	public List<ClientAssessmentDTO> getClientsAssesments(GenericSearchDTO search);
	public void setClientAssesment(ClientAssessmentDTO dto);
}
