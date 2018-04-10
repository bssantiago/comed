package com.mhc.dao;

import java.util.Calendar;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.exceptions.dao.DAOSystemException;

public class BiometricInfoDAOImpl extends BaseDAO implements BiometricInfoDAO {

	private static final String SELECT_BIOMETRIC_INFO = "SELECT * FROM comed_participants_biometrics WHERE biometric_id = ?";
	private static final String INSERT_BIOMETRIC_INFO = "INSERT INTO comed_participants_biometrics("
			+ "biometric_id, participant_id, sistolic, diastolic, height, weight, waist, body_fat, cholesterol, hdl, triglycerides, ldl, glucose, hba1c, tobacco_use)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	@Override
	public BiometricInfoDTO getBiometricInfo(Integer id) throws EmptyResultDataAccessException{
		try {
			BiometricInfoDTO binfo = jdbcTemplate.queryForObject(SELECT_BIOMETRIC_INFO, new Object[] { id },
					new BeanPropertyRowMapper<BiometricInfoDTO>(BiometricInfoDTO.class));
			return binfo;
		} catch (DAOSystemException dse) {
			throw dse;
		}
	}

	@Override
	public void saveBiometricInfo(BiometricInfoDTO bioInfo) {
		try {
			bioInfo.setCreation_date(Calendar.getInstance().getTime());
			Object[] obj = new Object[] { bioInfo.getBiometric_id(), bioInfo.getParticipant_id(), bioInfo.getSistolic(),
					bioInfo.getDiastolic(), bioInfo.getHeight(), bioInfo.getWeight(), bioInfo.getWaist(),
					bioInfo.getBody_fat(), bioInfo.getCholesterol(), bioInfo.getHdl(), bioInfo.getTriglycerides(),
					bioInfo.getLdl(), bioInfo.getGlucose(), bioInfo.getHba1c(), bioInfo.isTobacco_use() };

			jdbcTemplate.update(INSERT_BIOMETRIC_INFO, obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

	}

}
