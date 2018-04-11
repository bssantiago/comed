package com.mhc.dto;

import java.util.Date;

public class BiometricInfoDTO extends BaseParticipantDTO {
	private Integer biometric_id;
	private Integer participant_id;
	private float sistolic;
	private float diastolic;
	private float height;
	private float weight;
	private float waist;
	private float body_fat;
	private float cholesterol;
	private float hdl;
	private float triglycerides;
	private float ldl;
	private float glucose;
	private float hba1c;
	private boolean tobacco_use;
	private String program_display_name;
	private Date creation_date;
	private float duration;
	private float fasting;

	public Integer getBiometric_id() {
		return biometric_id;
	}

	public void setBiometric_id(Integer biometric_id) {
		this.biometric_id = biometric_id;
	}

	public Integer getParticipant_id() {
		return participant_id;
	}

	public void setParticipant_id(Integer participant_id) {
		this.participant_id = participant_id;
	}

	public float getSistolic() {
		return sistolic;
	}

	public void setSistolic(float sistolic) {
		this.sistolic = sistolic;
	}

	public float getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(float diastolic) {
		this.diastolic = diastolic;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getWaist() {
		return waist;
	}

	public void setWaist(float waist) {
		this.waist = waist;
	}

	public float getBody_fat() {
		return body_fat;
	}

	public void setBody_fat(float body_fat) {
		this.body_fat = body_fat;
	}

	public float getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(float cholesterol) {
		this.cholesterol = cholesterol;
	}

	public float getHdl() {
		return hdl;
	}

	public void setHdl(float hdl) {
		this.hdl = hdl;
	}

	public float getTriglycerides() {
		return triglycerides;
	}

	public void setTriglycerides(float triglycerides) {
		this.triglycerides = triglycerides;
	}

	public float getLdl() {
		return ldl;
	}

	public void setLdl(float ldl) {
		this.ldl = ldl;
	}

	public float getGlucose() {
		return glucose;
	}

	public void setGlucose(float glucose) {
		this.glucose = glucose;
	}

	public float getHba1c() {
		return hba1c;
	}

	public void setHba1c(float hba1c) {
		this.hba1c = hba1c;
	}

	public boolean isTobacco_use() {
		return tobacco_use;
	}

	public void setTobacco_use(boolean tobacco_use) {
		this.tobacco_use = tobacco_use;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public String getProgram_display_name() {
		return program_display_name;
	}

	public void setProgram_display_name(String program_display_name) {
		this.program_display_name = program_display_name;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getFasting() {
		return fasting;
	}

	public void setFasting(float fasting) {
		this.fasting = fasting;
	}
	
}
