package com.mhc.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.mhc.dao.queries.ParticipantConstants;

public class UtilsFunctions {

	public static double round(double d, int decimalPlace) {
		return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double calculateBMI(double height, double weight) {
		String aux = Double.toString(height);
		String[] aux2 = StringUtils.split(aux, ".");

		Double first = Double.parseDouble(aux2[0]);
		Double second = (aux2.length == 1) ? 0 : Double.parseDouble(aux2[1]);
		
		double height2 = Math.pow((first * 12) + second, 2);
		double bmi = 0;
		if (height2 != 0) {
			bmi = UtilsFunctions.round((weight / height2) * 703, ParticipantConstants.DECIMAL_PLACES);
		}
		return bmi;
	}
	
}
