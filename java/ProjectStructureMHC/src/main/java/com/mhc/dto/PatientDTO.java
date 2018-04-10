package com.mhc.dto;

import java.util.Date;

public class PatientDTO extends BiometricInfoDTO {
// cp.first_name,cp.last_name,cp.member_id,cp.date_of_birth,cca.program_display_name
	private String first_name;
	private String last_name;
	private String member_id;
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getProgram_display_name() {
		return program_display_name;
	}
	public void setProgram_display_name(String program_display_name) {
		this.program_display_name = program_display_name;
	}
	private Date date_of_birth;
	private String program_display_name;
	
}
