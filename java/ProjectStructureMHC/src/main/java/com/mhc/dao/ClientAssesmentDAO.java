package com.mhc.dao;

import com.mhc.dto.ClientAssessmentBaseDTO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.GenericSearchDTO;
import com.mhc.dto.SearchResultDTO;

public interface ClientAssesmentDAO {
	public SearchResultDTO<ClientAssessmentDTO> getClientsAssesments(GenericSearchDTO search);

	public void setClientAssesment(ClientAssessmentDTO dto);

	public boolean markAsDownload(ClientAssessmentBaseDTO dto) throws Exception;
}
