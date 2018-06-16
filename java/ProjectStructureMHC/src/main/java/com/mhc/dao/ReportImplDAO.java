package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dao.queries.ReportConstants;
import com.mhc.dto.BloodDTO;
import com.mhc.dto.HealthOverviewDTO;
import com.mhc.dto.ReportResultsDTO;
import com.mhc.dto.SimpleChartDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.util.UtilsFunctions;

public class ReportImplDAO extends BaseDAO<ReportResultsDTO> implements ReportDAO {
	private static final Logger LOG = Logger.getLogger(ReportImplDAO.class);

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
				double height = srs.getDouble("height");
				double weight = srs.getInt("weight");

				double bmi = UtilsFunctions.calculateBMI(height, weight);
				item.setBmi(bmi);
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
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

	@Override
	public List<SimpleChartDTO> bloodHistory(Integer pid) {
		List<SimpleChartDTO> result = new ArrayList<SimpleChartDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.BLOOD_HISTORY, params);

			while (srs.next()) {
				SimpleChartDTO item = new SimpleChartDTO(srs.getDouble("sistolic"), srs.getDouble("diastolic"),
						srs.getDate("create_date"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		return result;
	}

	@Override
	public List<SimpleChartDTO> cholesterolHistory(Integer pid) {
		List<SimpleChartDTO> result = new ArrayList<SimpleChartDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.CHOLESTEROL_HISTORY, params);

			while (srs.next()) {
				SimpleChartDTO item = new SimpleChartDTO(srs.getDouble("cholesterol"), srs.getDate("create_date"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		return result;
	}

	@Override
	public List<SimpleChartDTO> hdlHistory(Integer pid) {
		List<SimpleChartDTO> result = new ArrayList<SimpleChartDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.HDL_HISTORY, params);

			while (srs.next()) {
				SimpleChartDTO item = new SimpleChartDTO(srs.getDouble("hdl"), srs.getDate("create_date"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		return result;
	}

	@Override
	public List<SimpleChartDTO> ldlHistory(Integer pid) {
		List<SimpleChartDTO> result = new ArrayList<SimpleChartDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.LDL_HISTORY, params);

			while (srs.next()) {
				SimpleChartDTO item = new SimpleChartDTO(srs.getDouble("ldl"), srs.getDate("create_date"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		return result;
	}

	@Override
	public List<SimpleChartDTO> triglyceridesHistory(Integer pid) {
		List<SimpleChartDTO> result = new ArrayList<SimpleChartDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.TRIGLYCERIDES_HISTORY, params);

			while (srs.next()) {
				SimpleChartDTO item = new SimpleChartDTO(srs.getDouble("triglycerides"), srs.getDate("create_date"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		return result;
	}

	@Override
	public List<SimpleChartDTO> waistHistory(Integer pid) {
		List<SimpleChartDTO> result = new ArrayList<SimpleChartDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		try {
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ReportConstants.WAIST_HISTORY, params);

			while (srs.next()) {
				SimpleChartDTO item = new SimpleChartDTO(srs.getDouble("waist"), srs.getDate("create_date"));
				result.add(item);

			}
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		return result;
	}

}
