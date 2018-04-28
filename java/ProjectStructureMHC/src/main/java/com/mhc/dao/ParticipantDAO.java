package com.mhc.dao;

import java.io.File;
import java.util.List;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.LigthParticipantDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;

public interface ParticipantDAO {
	public Integer setParticipant(ParticipantsDTO dto);

	public void setParticipantBatch(List<ParticipantsDTO> participants);

	public Integer getParticipantByExternalId(long client_id, String external_id); 

	public List<String> getFirstNames(String firstname);

	public List<String> getLastNames(String lastname);

	public SearchResultDTO<LigthParticipantDTO> search(SearchDTO request);

	public File getTxt(Integer client_id, String program_id);
	
	public File getPdf(BiometricInfoDTO bio);

	public void bindParticipantWithClient(ParticipantsDTO pdto);
	
	
}
