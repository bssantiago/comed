package com.mhc.dao;

import com.mhc.dto.BiometricInfoDTO;

public interface BiometricInfoDAO {
	public BiometricInfoDTO getBiometricInfo(Integer id);
	public void saveBiometricInfo(BiometricInfoDTO bioInfo);

}
