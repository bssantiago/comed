package com.mhc.dto;

public class HealthOverviewDTO {
	
	public HealthOverviewDTO() {
		
	}
	
	
	public HealthOverviewDTO(double sistolic, double diastolic, double cholesterol, double hdl, double ldl,
			double triglycerides, double glucose, double hba1c, double waist, double body_fat) {
		super();
		this.sistolic = sistolic;
		this.diastolic = diastolic;
		this.cholesterol = cholesterol;
		this.hdl = hdl;
		this.ldl = ldl;
		this.triglycerides = triglycerides;
		this.glucose = glucose;
		this.hba1c = hba1c;
		this.waist = waist;
		this.body_fat = body_fat;
	}
	public Integer participant_id;
	public double sistolic;
	public double diastolic;
	public double cholesterol;
	public double hdl;
	public double ldl;
	public double triglycerides;
	public double glucose;
	public double hba1c;
	public double bmi;
	public double getSistolic() {
		return sistolic;
	}
	public void setSistolic(double sistolic) {
		this.sistolic = sistolic;
	}
	public double getDiastolic() {
		return diastolic;
	}
	public void setDiastolic(double diastolic) {
		this.diastolic = diastolic;
	}
	public double getCholesterol() {
		return cholesterol;
	}
	public void setCholesterol(double cholesterol) {
		this.cholesterol = cholesterol;
	}
	public double getHdl() {
		return hdl;
	}
	public void setHdl(double hdl) {
		this.hdl = hdl;
	}
	public double getLdl() {
		return ldl;
	}
	public void setLdl(double ldl) {
		this.ldl = ldl;
	}
	public double getTriglycerides() {
		return triglycerides;
	}
	public void setTriglycerides(double triglycerides) {
		this.triglycerides = triglycerides;
	}
	public double getGlucose() {
		return glucose;
	}
	public void setGlucose(double glucose) {
		this.glucose = glucose;
	}
	public double getHba1c() {
		return hba1c;
	}
	public void setHba1c(double hba1c) {
		this.hba1c = hba1c;
	}
	public double getBmi() {
		return bmi;
	}
	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	public double getWaist() {
		return waist;
	}
	public void setWaist(double waist) {
		this.waist = waist;
	}
	public double getBody_fat() {
		return body_fat;
	}
	public void setBody_fat(double body_fat) {
		this.body_fat = body_fat;
	}
	public double getBody_index() {
		return body_index;
	}
	public void setBody_index(double body_index) {
		this.body_index = body_index;
	}
	public double waist;
	public double body_fat;
	public double body_index;
}