package com.mhc.dao;

import java.util.List;

import com.mhc.dto.BloodDTO;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.dto.ReportResultsDTO;

public interface ReportDAO {
	public List<HealthOverviewDTO> healthOverviewReport(Integer pid);
	public ReportResultsDTO negativeReport();
	public ReportResultsDTO positiveReport();
	public List<BloodDTO> bloodReport();
}
