package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.BloodDTO;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.dto.ReportResultsDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.services.EncryptService;
import com.mhc.util.BiometricsConstants;
import com.mhc.util.ReportConstants;

public class ReportImplDAO extends BaseDAO implements ReportDAO {

	@Override
	public List<HealthOverviewDTO> healthOverviewReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportResultsDTO> negativeReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportResultsDTO> positiveReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BloodDTO> bloodReport() {
		
			Map<String, Object> params = new HashMap<String, Object>();
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.BLOOD_RESULTS_REPORT, params);
			List<BloodDTO> result = new ArrayList<BloodDTO>();
			if (srs.next()) {
				int year = Calendar.getInstance().get(Calendar.YEAR);

				double normal = srs.getDouble("normal");
				double preHypertension = srs.getDouble("preHypertension");
				double stage1 = srs.getDouble("stage1");
				double stage2 = srs.getDouble("stage2");
				result.add(this.createBloodItem(normal, preHypertension, stage1, stage2, year));
				
				double normal_prev_1 = srs.getDouble("normal_prev_1");
				double preHypertension_prev_1 = srs.getDouble("preHypertension_prev_1");
				double stage1_prev_1 = srs.getDouble("stage1_prev_1");
				double stage2_prev_1 = srs.getDouble("stage2_prev_1");
				result.add(this.createBloodItem(normal_prev_1, preHypertension_prev_1, stage1_prev_1, stage2_prev_1, year-1));
				
				double normal_prev_2 = srs.getDouble("normal_prev_2");
				double preHypertension_prev_2 = srs.getDouble("preHypertension_prev_2");
				double stage1_prev_2 = srs.getDouble("stage1_prev_2");
				double stage2_prev_2 = srs.getDouble("stage2_prev_2");
				result.add(this.createBloodItem(normal_prev_2, preHypertension_prev_2, stage1_prev_2, stage2_prev_2, year-2));
				
				double normal_prev_3 = srs.getDouble("normal_prev_3");
				double preHypertension_prev_3 = srs.getDouble("preHypertension_prev_3");
				double stage1_prev_3 = srs.getDouble("stage1_prev_3");
				double stage2_prev_3 = srs.getDouble("stage2_prev_3");
				result.add(this.createBloodItem(normal_prev_3, preHypertension_prev_3, stage1_prev_3, stage2_prev_3, year-3));

			}
			return result;
		
	}

	private BloodDTO createBloodItem(double n, double p, double s1, double s2, double y) {
		return new BloodDTO(n, p, s1, s2, y);
	}

	@Override
	protected Object[] toDataObject(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
