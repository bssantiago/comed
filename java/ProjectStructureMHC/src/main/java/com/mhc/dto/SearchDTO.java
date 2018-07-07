package com.mhc.dto;

import java.util.Date;

public class SearchDTO extends GenericSearchDTO {
	private String name;
	private String program;
	private String lastname;
	private Integer client;
	private String memberId;
	private Date dob;
	private String gender;
	private String dobString;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDobString() {
		return dobString;
	}

	public void setDobString(String dobString) {
		this.dobString = dobString;
	}
}
