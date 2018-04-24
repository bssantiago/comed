package com.mhc.dto;

public class StudyResultDTO {
	private String measure;
	private String desirable;
	private String result;
	
	public StudyResultDTO() {
		
	}
	
	public StudyResultDTO(String m,String d,String r) {
		this.measure = m;
		this.desirable = d;
		this.result = r;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDesirable() {
		return desirable;
	}

	public void setDesirable(String desirable) {
		this.desirable = desirable;
	}
}
