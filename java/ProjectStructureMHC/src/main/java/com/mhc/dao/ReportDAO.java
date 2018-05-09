package com.mhc.dao;

import java.util.List;

import com.mhc.dto.BloodDTO;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.dto.ReportResultsDTO;
import com.mhc.dto.SimpleChartDTO;

public interface ReportDAO {
	public List<HealthOverviewDTO> healthOverviewReport(Integer pid);
	public ReportResultsDTO negativeReport();
	public ReportResultsDTO positiveReport();
	public List<BloodDTO> bloodReport();
	
	public List<SimpleChartDTO> bloodHistory(Integer pid); 
	
	public List<SimpleChartDTO> cholesterolHistory(Integer pid); 
	public List<SimpleChartDTO> hdlHistory(Integer pid); 
	public List<SimpleChartDTO> ldlHistory(Integer pid); 
	public List<SimpleChartDTO> triglyceridesHistory(Integer pid);
	public List<SimpleChartDTO> waistHistory(Integer pid);
}
