package com.mhc.dao;

import java.util.Calendar;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.exceptions.dao.DAOSystemException;

public class BiometricInfoDAOImpl extends BaseDAO<BiometricInfoDTO> implements BiometricInfoDAO {

	private static final String SELECT_BIOMETRIC_INFO = "select cp.first_name,cp.last_name,cp.member_id,cp.date_of_birth,cca.program_display_name, cpb.* from public.comed_participants as cp "
			+ "	LEFT JOIN public.comed_participants_biometrics as cpb" + "		on cp.id = cpb.participant_id"
			+ "	LEFT JOIN comed_client_assessment as cca" + "		on cp.client_id = cca.client_id "
			+ "where cp.id = ? " + "and cca.status = true;";

	private static final String INSERT_BIOMETRIC_INFO = "INSERT INTO comed_participants_biometrics("
			+ "participant_id, sistolic, diastolic, height, weight, waist, body_fat, cholesterol, hdl, triglycerides, ldl, glucose, hba1c, tobacco_use, duration,fasting)"
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String UPDATE_BIOMETRIC_INFO = "UPDATE public.comed_participants_biometrics"
			+ "	SET participant_id=?, sistolic=?, diastolic=?, height=?, weight=?, waist=?, body_fat=?, cholesterol=?, hdl=?, triglycerides=?, ldl=?, glucose=?, hba1c=?, tobacco_use=?,fasting=?";

	@Override
	public BiometricInfoDTO getBiometricInfo(Integer id) throws EmptyResultDataAccessException {
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
			Object[] obj = toDataObject(bioInfo);
			jdbcTemplate.update(INSERT_BIOMETRIC_INFO, obj);
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
			Object[] obj = toDataObject(bioInfo);
			jdbcTemplate.update(UPDATE_BIOMETRIC_INFO + " WHERE biometric_id=" + bioInfo.getBiometric_id() + ";", obj);
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
				bioInfo.getGlucose(), bioInfo.getHba1c(), bioInfo.isTobacco_use(), bioInfo.getDuration(), bioInfo.getFasting() };
		return obj;
	}

}
