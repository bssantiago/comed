package com.mhc.dao.queries;

public class BiometricsConstants {
	public static final String SELECT_BIOMETRIC_INFO = "select " + "	cp.first_name as first_name,"
			+ "	cp.last_name as last_name," + "	cp.member_id as member_id," + "	cp.date_of_birth as date_of_birth,"
			+ "	cca.program_display_name as program_display_name," + "	cca.reward_date as reward_date,"
			+ "	cpb.biometric_id as biometric_id," + "	cpb.participant_id as participant_id,"
			+ "	cpb.sistolic as sistolic," + "	cpb.diastolic as diastolic," + "	cpb.height as height," + "cpb.draw_type as draw_type,"
			+ "	cpb.weight as weight," + "	cpb.waist as waist," + "	cpb.body_fat as body_fat,"
			+ "	cpb.cholesterol as cholesterol," + "	cpb.hdl as hdl," + "	cpb.triglycerides as triglycerides,"
			+ "	cpb.ldl as ldl," + "	cpb.glucose as glucose," + "	cpb.hba1c as hba1c,"
			+ "	cpb.tobacco_use as tobacco_use," + "	cpb.fasting as fasting," + "	cpb.create_date as create_date,"
			+ "	cpb.duration as duration"
			+ " from public.comed_participants as cp LEFT JOIN public.comed_participants_biometrics as cpb on cp.id = cpb.participant_id LEFT JOIN comed_client_assessment as cca	on cp.client_id = cca.client_id "
			+ "where cp.id = ? and cca.status = true ORDER BY create_date DESC LIMIT 1;";

	public static final String FIND_BIOMETRICS_RECORDS = "select Count(*) as count from comed_participants_biometrics where participant_id = :participant_id";

	public static final String SELECT_BIOMETRICS_BASICS = "select * from comed_participants cp  "
			+ "LEFT JOIN comed_client_assessment as cca	on cp.client_id = cca.client_id "
			+"LEFT JOIN public.comed_participants_biometrics as cpb on cp.id = cpb.participant_id "
			+ "where cca.status = true and cp.id = :participant_id;";

	public static final String INSERT_BIOMETRIC_INFO = "INSERT INTO comed_participants_biometrics("
			+ "participant_id, sistolic, diastolic, height, weight, waist, body_fat, cholesterol, hdl, triglycerides, ldl, glucose, hba1c, tobacco_use, duration,fasting,create_date,draw_type)"
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static final String UPDATE_BIOMETRIC_INFO = "UPDATE public.comed_participants_biometrics"
			+ "	SET sistolic=?, diastolic=?, height=?, weight=?, waist=?, body_fat=?, cholesterol=?, hdl=?, triglycerides=?, ldl=?, glucose=?, hba1c=?, tobacco_use=?,fasting=?,draw_type=?";

	public static final Float MIN_SYSTOLIC = Float.parseFloat("90");
	public static final Float MAX_SYSTOLIC = Float.parseFloat("180");
	public static final Float MIN_DIASTOLIC = Float.parseFloat("60");
	public static final Float MAX_DIASTOLIC = Float.parseFloat("110");
	public static final Float MIN_HEIGHT = Float.parseFloat("4");
	public static final Float MAX_HEIGHT = Float.parseFloat("7");
	public static final Float MIN_HEIGHT_INCHES = Float.parseFloat("0");
	public static final Float MAX_HEIGHT_INCHES = Float.parseFloat("11");
	public static final Float MIN_WEIGHT = Float.parseFloat("80");
	public static final Float MAX_WEIGHT = Float.parseFloat("300");
	public static final Float MIN_BODY_FAT = Float.parseFloat("5");
	public static final Float MAX_BODY_FAT = Float.parseFloat("65");
	public static final Float MIN_CHOLESTEROL = Float.parseFloat("80");
	public static final Float MAX_CHOLESTEROL = Float.parseFloat("200");
	public static final Float MIN_HDL = Float.parseFloat("20");
	public static final Float MAX_HDL = Float.parseFloat("80");
	public static final Float MIN_TRIGLYCERIDES = Float.parseFloat("129");
	public static final Float MAX_TRIGLYCERIDES = Float.parseFloat("220");
	public static final Float MIN_ILD = Float.parseFloat("80");
	public static final Float MAX_ILD = Float.parseFloat("180");
	public static final Float MIN_GLUCOSE = Float.parseFloat("60");
	public static final Float MAX_GLUCOSE = Float.parseFloat("220");
	public static final Float MIN_HBA1C = Float.parseFloat("5");
	public static final Float MAX_HBA1C = Float.parseFloat("7");
	
}
