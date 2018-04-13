package com.mhc.dao;

import java.util.List;

import com.mhc.dto.BaseParticipantDTO;
import com.mhc.dto.LigthParticipantDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;

public interface ParticipantDAO {
	public void setParticipant(ParticipantsDTO dto);
	public void setParticipantBatch(List<ParticipantsDTO> participants);
	public List<String> getFirstNames(String firstname);
	public List<String> getLastNames(String lastname);
	public SearchResultDTO search(SearchDTO request);
}
