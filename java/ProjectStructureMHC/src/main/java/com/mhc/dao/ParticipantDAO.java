package com.mhc.dao;

import java.util.List;

import com.mhc.dto.BaseParticipantDTO;
import com.mhc.dto.LigthParticipantDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;

public interface ParticipantDAO {
	public void setParticipant(ParticipantsDTO dto);
	public void setParticipantBatch(List<ParticipantsDTO> participants);
	public Integer getParticipantByKordinatorId(ParticipantsDTO dto);
	public List<String> getFirstNames(String firstname);
	public List<String> getLastNames(String lastname);
	public List<LigthParticipantDTO> search(SearchDTO request);
}
