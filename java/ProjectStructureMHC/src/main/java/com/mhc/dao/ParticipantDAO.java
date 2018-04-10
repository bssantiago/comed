package com.mhc.dao;

import java.util.List;

import com.mhc.dto.ParticipantsDTO;

public interface ParticipantDAO {
	public void setParticipant(ParticipantsDTO dto);
	public void setParticipantBatch(List<ParticipantsDTO> participants);
}
