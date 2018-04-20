package com.mhc.dto;

import java.util.Date;

public class ProgramDTO {
	private Integer program_id;
	private Date reward_date;

	public Integer getProgram_id() {
		return program_id;
	}

	public void setProgram_id(Integer program_id) {
		this.program_id = program_id;
	}

	public Date getReward_date() {
		return reward_date;
	}

	public void setReward_date(Date reward_date) {
		this.reward_date = reward_date;
	}

	public String getProgram_display_date() {
		return program_display_date;
	}

	public void setProgram_display_date(String program_display_date) {
		this.program_display_date = program_display_date;
	}

	private String program_display_date;
}
