package com.mhc.dto;

import java.util.Date;

public class SimpleChartDTO {
	
	public SimpleChartDTO(double y,Date x) {
		this.Y = y;
		this.X = x;
		
	}
	
	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public double getY1() {
		return Y1;
	}

	public void setY1(double y1) {
		Y1 = y1;
	}

	public Date getX() {
		return X;
	}

	public void setX(Date x) {
		X = x;
	}

	public SimpleChartDTO(double y,double y1,Date x) {
		this.Y = y;
		this.X = x;
		this.Y1 = y1;
	}
	
	private double Y;
	private double Y1;
	private Date X;
}
