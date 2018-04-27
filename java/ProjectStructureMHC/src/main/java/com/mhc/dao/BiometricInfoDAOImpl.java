package com.mhc.dao;

import com.mhc.util.BiometricsConstants;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.services.EncryptService;

public class BiometricInfoDAOImpl extends BaseDAO<BiometricInfoDTO> implements BiometricInfoDAO {

	@Override
	public BiometricInfoDTO getBiometricInfo(Integer id) throws EmptyResultDataAccessException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("participant_id", id);
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(BiometricsConstants.FIND_BIOMETRICS_RECORDS,
					params);
			BiometricInfoDTO result = dameAlTontoBiometrico(id);
			if (srs.next()) {
				int count = srs.getInt("count");
				result = (count == 0) ? result : dameAlPibeBiometrico(id);
			}
			return result;
		} catch (DAOSystemException dse) {
			throw dse;
		}
	}

	private BiometricInfoDTO dameAlPibeBiometrico(Integer id) {
		try {
			BiometricInfoDTO binfo = jdbcTemplate.queryForObject(BiometricsConstants.SELECT_BIOMETRIC_INFO,
					new Object[] { id }, new BeanPropertyRowMapper<BiometricInfoDTO>(BiometricInfoDTO.class));
			binfo.setFirst_name(EncryptService.decryptStringDB(binfo.getFirst_name()));
			binfo.setLast_name(EncryptService.decryptStringDB(binfo.getLast_name()));
			return binfo;
		} catch (DAOSystemException dse) {
			throw dse;
		}
	}

	private BiometricInfoDTO dameAlTontoBiometrico(Integer id) {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("participant_id", id);
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(BiometricsConstants.SELECT_BIOMETRICS_BASICS,
					params);
			BiometricInfoDTO result;
			if (srs.next()) {
				String first_name = EncryptService.decryptStringDB(srs.getString("first_name"));
				String last_name = EncryptService.decryptStringDB(srs.getString("last_name"));
				String member_id = srs.getString("member_id");
				String program_display_name = srs.getString("program_display_name");
				Date date_of_birth = srs.getDate("date_of_birth");
				String draw_type = srs.getString("draw_type");
				result = new BiometricInfoDTO(first_name, last_name, member_id, date_of_birth, draw_type, id,
						program_display_name);
			} else {
				result = new BiometricInfoDTO();
			}
			return result;
		} catch (DAOSystemException dse) {
			throw dse;
		}
	}

	@Override
	public void saveBiometricInfo(BiometricInfoDTO bioInfo) {
		try {
			bioInfo.setCreation_date(Calendar.getInstance().getTime());
			Object[] obj = toDataObject(bioInfo);
			jdbcTemplate.update(BiometricsConstants.INSERT_BIOMETRIC_INFO, obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

	}

	@Override
	public void updateBiometricInfo(BiometricInfoDTO bioInfo) {
		try {
			bioInfo.setCreation_date(Calendar.getInstance().getTime());
			Object[] obj = toUpdateObject(bioInfo);
			jdbcTemplate.update(BiometricsConstants.UPDATE_BIOMETRIC_INFO + " WHERE biometric_id="
					+ bioInfo.getBiometric_id() + ";", obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

	}

	@Override
	protected Object[] toDataObject(BiometricInfoDTO bioInfo) {
		Object[] obj = new Object[] { bioInfo.getParticipant_id(), bioInfo.getSistolic(), bioInfo.getDiastolic(),
				bioInfo.getHeight(), bioInfo.getWeight(), bioInfo.getWaist(), bioInfo.getBody_fat(),
				bioInfo.getCholesterol(), bioInfo.getHdl(), bioInfo.getTriglycerides(), bioInfo.getLdl(),
				bioInfo.getGlucose(), bioInfo.getHba1c(), bioInfo.isTobacco_use(), bioInfo.getDuration(),
				bioInfo.isFasting(),bioInfo.getCreation_date(),bioInfo.getDraw_type() };
		return obj;
	}

	private Object[] toUpdateObject(BiometricInfoDTO bioInfo) {
		Object[] obj = new Object[] { bioInfo.getSistolic(), bioInfo.getDiastolic(), bioInfo.getHeight(),
				bioInfo.getWeight(), bioInfo.getWaist(), bioInfo.getBody_fat(), bioInfo.getCholesterol(),
				bioInfo.getHdl(), bioInfo.getTriglycerides(), bioInfo.getLdl(), bioInfo.getGlucose(),
				bioInfo.getHba1c(), bioInfo.isTobacco_use(), bioInfo.isFasting(),bioInfo.getDraw_type() };
		return obj;
	}

}
