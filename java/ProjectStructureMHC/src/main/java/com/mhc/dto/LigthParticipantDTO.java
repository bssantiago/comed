package com.mhc.dto;

import java.util.Date;

public class LigthParticipantDTO {
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getParticipant_id() {
		return participant_id;
	}
	public void setParticipant_id(int participants_id) {
		this.participant_id = participants_id;
	}
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	private String external_id;
	private String member_id;
	private String last_name;
	private String first_name;
	private String address;
	private Date date_of_birth;
	private int participant_id;
}
