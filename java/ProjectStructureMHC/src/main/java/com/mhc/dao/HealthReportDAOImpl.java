package com.mhc.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.util.UtilsFunctions;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class HealthReportDAOImpl extends BaseDAO<ParticipantsDTO> implements HealthReportDAO {

	@Override
	public JasperPrint getReport(BiometricInfoDTO dto) throws SQLException {
		// Compile jrxml file.
		JasperReport jasperReport;
		JasperPrint jasperPrint = null;
		// Parameters for report

		try {
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("com/mhc/reports/health_report.jasper");
			jasperReport = (JasperReport) JRLoader.loadObject(in);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("PatientID", dto.getParticipant_id());
			String name = dto.getFirst_name();
			if (name !=  null) {
				name = String.valueOf(name.charAt(0)) + ".";
				name = StringUtils.upperCase(name);
			}
			parameters.put("FullName", name + " " + dto.getLast_name());
			double bmi = UtilsFunctions.calculateBMI(dto.getHeight(), dto.getWeight());
			parameters.put("bmi",bmi);
			parameters.put("RowReport", "com/mhc/reports/row_overview.jasper");
			parameters.put("ChartReport", "com/mhc/reports/charts.jasper");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					this.jdbcTemplate.getDataSource().getConnection());
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jasperPrint;
	}

}
