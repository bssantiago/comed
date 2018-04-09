package com.mhc.dao;

import java.util.List;

import com.mhc.dto.ClientAssessmentDTO;

public interface ClientAssesmentDAO {
	public List<ClientAssessmentDTO> getClientsAssesments();
	public void setClientAssesment(ClientAssessmentDTO dto);
}
