package com.mhc.dto;

public class ReportResultsDTO {
	public double sistolicDiastolic;
	public double tricerides;
	public double cholesterol;
	public double hdlM;
	public double hdlF;
	public double ldl;

	public ReportResultsDTO(double sistolicDiastolic, double tricerides, double cholesterol, double hdlM, double hdlF,
			double ldl) {
		super();
		this.sistolicDiastolic = sistolicDiastolic;
		this.tricerides = tricerides;
		this.cholesterol = cholesterol;
		this.hdlM = hdlM;
		this.hdlF = hdlF;
		this.ldl = ldl;
	}
}
