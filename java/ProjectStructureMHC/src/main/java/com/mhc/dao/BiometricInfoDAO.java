package com.mhc.dao;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.PatientDTO;

public interface BiometricInfoDAO {

	public PatientDTO getBiometricInfo(Integer id);
	public void saveBiometricInfo(BiometricInfoDTO bioInfo);

}
