package com.mhc.dto;

import java.util.Date;

public class ClientAssessmentDTO {

	private int client_id;
	private String program_id;
	private int calendar_year;
	private Date program_start_date;
	private Date program_end_date;
	private String program_display_name;
	private int extended_screenings;
	private String created_by;
	private Date creation_date;
	private String last_update_by;
	private Date last_update_date;
	private String file;
	private String file_name;
	private boolean status;
	private Date reward_date;
	private int full_count;
	
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String program_id) {
		this.program_id = program_id;
	}
	public int getCalendar_year() {
		return calendar_year;
	}
	public void setCalendar_year(int calendar_year) {
		this.calendar_year = calendar_year;
	}
	public Date getProgram_start_date() {
		return program_start_date;
	}
	public void setProgram_start_date(Date program_start_date) {
		this.program_start_date = program_start_date;
	}
	public Date getProgram_end_date() {
		return program_end_date;
	}
	public void setProgram_end_date(Date program_end_date) {
		this.program_end_date = program_end_date;
	}
	public String getProgram_display_name() {
		return program_display_name;
	}
	public void setProgram_display_name(String program_display_name) {
		this.program_display_name = program_display_name;
	}
	public int getExtended_screenings() {
		return extended_screenings;
	}
	public void setExtended_screenings(int extended_screenings) {
		this.extended_screenings = extended_screenings;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	public String getLast_update_by() {
		return last_update_by;
	}
	public void setLast_update_by(String last_update_by) {
		this.last_update_by = last_update_by;
	}
	public Date getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getReward_date() {
		return reward_date;
	}
	public void setReward_date(Date reward_date) {
		this.reward_date = reward_date;
	}
	public int getFull_count() {
		return full_count;
	}
	public void setFull_count(int full_count) {
		this.full_count = full_count;
	}	

}
