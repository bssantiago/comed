package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dao.queries.BiometricsConstants;
import com.mhc.dto.BiometricInfoDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.services.EncryptService;

public class BiometricInfoDAOImpl extends BaseDAO<BiometricInfoDTO> implements BiometricInfoDAO {
	private static final Logger LOG = Logger.getLogger(BiometricInfoDAOImpl.class);
	

	@Override
	public BiometricInfoDTO getBiometricInfo(Integer id) throws EmptyResultDataAccessException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("participant_id", id);
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(BiometricsConstants.FIND_BIOMETRICS_RECORDS,
					params);
			BiometricInfoDTO result;
			int count = 0;
			if (srs.next()) {
				count = srs.getInt("count");
			}
			result = (count == 0) ? getBiometricBasicInfo(id) : getCompleteBiometricInfo(id);

			return result;
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		}
	}

	@Override
	public void saveBiometricInfo(BiometricInfoDTO bioInfo) {
		try {
			validateMinMaxBiometric(bioInfo);
			if (bioInfo.getCreation_date() == null) {
				bioInfo.setCreation_date(Calendar.getInstance().getTime());
			}
			Object[] obj = toDataObject(bioInfo);
			jdbcTemplate.update(BiometricsConstants.INSERT_BIOMETRIC_INFO, obj);
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}

	}

	public void saveBiometricInfoBatch(List<BiometricInfoDTO> bios) {
		try {
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for (BiometricInfoDTO b : bios) {
				Object[] obj = toDataObject(b);
				batchArgs.add(obj);
			}
			jdbcTemplate.batchUpdate(BiometricsConstants.INSERT_BIOMETRIC_INFO, batchArgs);
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
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
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}

	}

	private Object[] toDataObject(BiometricInfoDTO bioInfo) {
		Object[] obj = new Object[] { bioInfo.getParticipant_id(), bioInfo.getSistolic(), bioInfo.getDiastolic(),
				bioInfo.getHeight(), bioInfo.getWeight(), bioInfo.getWaist(), bioInfo.getBody_fat(),
				bioInfo.getCholesterol(), bioInfo.getHdl(), bioInfo.getTriglycerides(), bioInfo.getLdl(),
				bioInfo.getGlucose(), bioInfo.getHba1c(), bioInfo.isTobacco_use(), bioInfo.getDuration(),
				bioInfo.isFasting(), bioInfo.getCreation_date(), bioInfo.getDraw_type() };
		return obj;
	}

	private Object[] toUpdateObject(BiometricInfoDTO bioInfo) {
		Object[] obj = new Object[] { bioInfo.getSistolic(), bioInfo.getDiastolic(), bioInfo.getHeight(),
				bioInfo.getWeight(), bioInfo.getWaist(), bioInfo.getBody_fat(), bioInfo.getCholesterol(),
				bioInfo.getHdl(), bioInfo.getTriglycerides(), bioInfo.getLdl(), bioInfo.getGlucose(),
				bioInfo.getHba1c(), bioInfo.isTobacco_use(), bioInfo.isFasting(), bioInfo.getDraw_type() };
		return obj;
	}

	private BiometricInfoDTO getCompleteBiometricInfo(Integer id) {
		try {
			BiometricInfoDTO binfo = jdbcTemplate.queryForObject(BiometricsConstants.SELECT_BIOMETRIC_INFO,
					new Object[] { id }, new BeanPropertyRowMapper<BiometricInfoDTO>(BiometricInfoDTO.class));
			binfo.setFirst_name(EncryptService.decryptStringDB(binfo.getFirst_name()));
			binfo.setLast_name(EncryptService.decryptStringDB(binfo.getLast_name()));
			return binfo;
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		}
	}

	private BiometricInfoDTO getBiometricBasicInfo(Integer id) {
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
			LOG.error(dse.getMessage());
			throw dse;
		}
	}

	private void validateMinMaxBiometric(BiometricInfoDTO dto) {
		validateField("Systolic", dto.getSistolic(), BiometricsConstants.MIN_SYSTOLIC, BiometricsConstants.MAX_SYSTOLIC);
		validateField("Diastolic", dto.getDiastolic(), BiometricsConstants.MIN_DIASTOLIC, BiometricsConstants.MAX_DIASTOLIC);
		validateField("Weight", dto.getWeight(), BiometricsConstants.MIN_WEIGHT, BiometricsConstants.MAX_WEIGHT);
		validateField("BodyFat", dto.getBody_fat(), BiometricsConstants.MIN_BODY_FAT, BiometricsConstants.MAX_BODY_FAT);
		validateField("Cholesterol", dto.getCholesterol(), BiometricsConstants.MIN_CHOLESTEROL, BiometricsConstants.MAX_CHOLESTEROL);
		validateField("HDL", dto.getHdl(), BiometricsConstants.MIN_HDL, BiometricsConstants.MAX_HDL);
		validateField("Triglycerides", dto.getTriglycerides(), BiometricsConstants.MIN_TRIGLYCERIDES, BiometricsConstants.MAX_TRIGLYCERIDES);
		validateField("Idl", dto.getLdl(), BiometricsConstants.MIN_ILD, BiometricsConstants.MAX_ILD);
		validateField("Glucose", dto.getGlucose(), BiometricsConstants.MIN_GLUCOSE, BiometricsConstants.MAX_GLUCOSE);
		validateField("hba1c", dto.getHba1c(), BiometricsConstants.MIN_HBA1C, BiometricsConstants.MAX_HBA1C);
	}

	private void validateField(String fieldName, Double value, Float min, Float max) {
		if (value < min || value > max) {
			Object[] args = new Object[] { fieldName, min, max };
			throw new DAOSystemException(messageSource.getMessage("invalid.range.field", args, null));
		}
	}

}
