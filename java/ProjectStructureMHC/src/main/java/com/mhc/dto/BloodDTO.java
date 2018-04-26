package com.mhc.dto;

public class BloodDTO {
	
	
	public BloodDTO(double normal, double preHypertension, double stage1, double stage2, double year) {
		super();
		this.normal = normal;
		this.preHypertension = preHypertension;
		this.stage1 = stage1;
		this.stage2 = stage2;
		this.year = year;
	}
	public double normal;
	public double preHypertension;
	public double stage1;
	public double stage2;
	public double noResult;
	public double year;
}