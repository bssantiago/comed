package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.BloodDTO;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.dto.ReportResultsDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.util.ReportConstants;

public class ReportImplDAO extends BaseDAO implements ReportDAO {

	@Override
	public List<HealthOverviewDTO> healthOverviewReport(Integer pid) {
		List<HealthOverviewDTO> result = new ArrayList<HealthOverviewDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.HEALTH_OVERVIEW_RESULTS_REPORT,
					params);

			while (srs.next()) {
				HealthOverviewDTO item = new HealthOverviewDTO(srs.getDouble("sistolic"), srs.getDouble("diastolic"),
						srs.getDouble("cholesterol"), srs.getDouble("hdl"), srs.getDouble("ldl"),
						srs.getDouble("triglycerides"), srs.getDouble("glucose"), srs.getDouble("hba1c"),
						srs.getDouble("waist"), srs.getDouble("body_fat"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
		return result;
	}

	@Override
	public ReportResultsDTO negativeReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportResultsDTO positiveReport() {

		Map<String, Object> params = new HashMap<String, Object>();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.POSITIVE_RESULTS_REPORT, params);
		ReportResultsDTO result = null;
		if (srs.next()) {
			result = new ReportResultsDTO(srs.getDouble("SistolicDiastolic"), srs.getDouble("Tricerides"),
					srs.getDouble("Cholesterol"), srs.getDouble("HdlM"), srs.getDouble("HdlF"), srs.getDouble("Ldl"));

		}
		return result;
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
			result.add(this.createBloodItem(normal_prev_1, preHypertension_prev_1, stage1_prev_1, stage2_prev_1,
					year - 1));

			double normal_prev_2 = srs.getDouble("normal_prev_2");
			double preHypertension_prev_2 = srs.getDouble("preHypertension_prev_2");
			double stage1_prev_2 = srs.getDouble("stage1_prev_2");
			double stage2_prev_2 = srs.getDouble("stage2_prev_2");
			result.add(this.createBloodItem(normal_prev_2, preHypertension_prev_2, stage1_prev_2, stage2_prev_2,
					year - 2));

			double normal_prev_3 = srs.getDouble("normal_prev_3");
			double preHypertension_prev_3 = srs.getDouble("preHypertension_prev_3");
			double stage1_prev_3 = srs.getDouble("stage1_prev_3");
			double stage2_prev_3 = srs.getDouble("stage2_prev_3");
			result.add(this.createBloodItem(normal_prev_3, preHypertension_prev_3, stage1_prev_3, stage2_prev_3,
					year - 3));

		}
		return result;

	}

	private BloodDTO createBloodItem(double n, double p, double s1, double s2, double y) {
		return new BloodDTO(n, p, s1, s2, y);
	}

	private ReportResultsDTO createReportPositiveNegative(double sd, double t, double c, double hm, double hf,
			double l) {
		return new ReportResultsDTO(sd, t, c, hm, hf, l);
	}

	@Override
	protected Object[] toDataObject(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
