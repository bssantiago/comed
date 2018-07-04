package com.mhc.dao;

import java.sql.SQLException;

import com.mhc.dto.BiometricInfoDTO;

import net.sf.jasperreports.engine.JasperPrint;

public interface HealthReportDAO {
	
	public JasperPrint getReport(BiometricInfoDTO dto) throws SQLException; 
	
}
