package com.mhc.dto;

import java.util.Date;

import com.sun.istack.Nullable;

public class BiometricInfoDTO extends BaseParticipantDTO {
	
	private Integer biometric_id;
	private Integer participant_id;
	@Nullable 
	private double sistolic;
	@Nullable 
	private double diastolic;
	@Nullable 
	private double height;
	@Nullable 
	private double weight;
	@Nullable 
	private double waist;
	@Nullable 
	private double body_fat;
	@Nullable 
	private double cholesterol;
	@Nullable 
	private double hdl;
	@Nullable 
	private double triglycerides;
	@Nullable 
	private double ldl;
	@Nullable 
	private double glucose;
	@Nullable 
	private double hba1c;
	@Nullable 
	private boolean tobacco_use;
	@Nullable 
	private String program_display_name;
	@Nullable 
	private Date reward_date;
	private Date creation_date;
	@Nullable 
	private double duration;
	@Nullable 
	private boolean fasting;

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

	public double getSistolic() {
		return sistolic;
	}

	public void setSistolic(Float sistolic) {
		this.sistolic = sistolic;
	}

	public double getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(Float diastolic) {
		this.diastolic = diastolic;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public double getWaist() {
		return waist;
	}

	public void setWaist(Float waist) {
		this.waist = waist;
	}

	public double getBody_fat() {
		return body_fat;
	}

	public void setBody_fat(Float body_fat) {
		this.body_fat = body_fat;
	}

	public double getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(Float cholesterol) {
		this.cholesterol = cholesterol;
	}

	public double getHdl() {
		return hdl;
	}

	public void setHdl(Float hdl) {
		this.hdl = hdl;
	}

	public double getTriglycerides() {
		return triglycerides;
	}

	public void setTriglycerides(Float triglycerides) {
		this.triglycerides = triglycerides;
	}

	public double getLdl() {
		return ldl;
	}

	public void setLdl(Float ldl) {
		this.ldl = ldl;
	}

	public double getGlucose() {
		return glucose;
	}

	public void setGlucose(Float glucose) {
		this.glucose = glucose;
	}

	public double getHba1c() {
		return hba1c;
	}

	public void setHba1c(Float hba1c) {
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

	public double getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public boolean isFasting() {
		return fasting;
	}

	public void setFasting(boolean fasting) {
		this.fasting = fasting;
	}

	public Date getReward_date() {
		return reward_date;
	}

	public void setReward_date(Date reward_date) {
		this.reward_date = reward_date;
	}
	
}
