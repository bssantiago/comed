package com.mhc.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDTO {
	private int pages;
	private List<LigthParticipantDTO> participants;
	
	public SearchResultDTO() {
		this.participants = new ArrayList<LigthParticipantDTO>();
		this.pages = 0;
	}
	
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<LigthParticipantDTO> getParticipants() {
		return participants;
	}

	public void setParticipants(List<LigthParticipantDTO> participants) {
		this.participants = participants;
	}
}
