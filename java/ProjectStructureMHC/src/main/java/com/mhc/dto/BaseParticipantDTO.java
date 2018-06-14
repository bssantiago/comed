package com.mhc.dto;

import java.util.Date;

public class BaseParticipantDTO {
	protected String first_name;
	protected String last_name;
	protected String member_id;
	protected Date date_of_birth;
	protected String draw_type;
	protected Integer client_id;
	protected String external_id;
	
	public BaseParticipantDTO() {
		
	}
	
	public BaseParticipantDTO(String first_name, String last_name, String member_id, Date date_of_birth,
			String draw_type) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.member_id = member_id;
		this.date_of_birth = date_of_birth;
		this.draw_type = draw_type;
	}
	
	public BaseParticipantDTO(String first_name, String last_name, String member_id, Date date_of_birth,
			String draw_type,String program) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.member_id = member_id;
		this.date_of_birth = date_of_birth;
		this.draw_type = draw_type;
	}

	

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

	public String getDraw_type() {
		return draw_type;
	}

	public void setDraw_type(String draw_type) {
		this.draw_type = draw_type;
	}

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public String getExternal_id() {
		return external_id;
	}

	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}

	
	
}
