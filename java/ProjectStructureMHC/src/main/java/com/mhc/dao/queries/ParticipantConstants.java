package com.mhc.dao.queries;

public class ParticipantConstants {
	public static final String BIND_PARTICIPANT_CLIENT = "update comed_participants set external_id = :external_id where id= :participant_id and external_id IS NULL";
	public static final String GET_FILE_QUERY = "select cp.first_name as first_name, cp.gender as gender, "
			+ "cp.last_name as last_name, cp.date_of_birth as date_of_birth, cp.member_id as member_id, "
			+ "cc.vendor as vendor, cc.id as client_id, cc.highmark_client_id as highmark_client_id,"
			+ "cc.highmark_site_code as highmark_site_code, cc.name as client_name, "
			+ "cpb.cholesterol as cholesterol, cpb.fasting as fasting, cpb.glucose as glucose,"
			+ "cpb.ldl as ldl, cpb.hdl as hdl, cpb.triglycerides as triglycerides, cpb.height as height,"
			+ "cpb.weight as weight, cpb.waist as waist, cpb.body_fat as body_fat , cca.marked as marked, cpb.draw_type as draw_type "
			+ "FROM comed_participants cp  LEFT JOIN comed_clients cc on cp.client_id = cc.id "
			+ "LEFT JOIN comed_client_assessment cca on cc.id = cca.client_id "
			+ "LEFT JOIN comed_participants_biometrics cpb  on cp.id = cpb.participant_id "
			+ "where cca.program_id = :program_id and cca.client_id = :client_id and cp.is_from_file = true " 
			+ "and (external_participant = false OR external_participant is NULL) and cca.status = true and cp.status=:status "
			+ "and cpb.create_date <= cca.program_end_date and cpb.create_date >= cca.program_start_date and (cpb.downloaded = false or cpb.downloaded is NULL)";
	
	public static final String UPDATE_BIOMETRIC_DOWNLOAD = "UPDATE comed_participants_biometrics " 
			+"SET downloaded = true where participant_id in (select cp.id FROM comed_participants cp  LEFT JOIN comed_client_assessment cca on cp.client_id = cca.client_id " 
			+"where cca.client_id = :client_id and cca.program_id = :program_id)";

	public static final String INSERT_PARTICIPANT_NAMED_QUERY = "WITH upsert AS (UPDATE comed_participants SET first_name=:first_name, last_name=:last_name, middle_initial=:middle_initial, addr1=:addr1,"
			+ " addr2=:addr2, city=:city, state=:state, postal_code=:postal_code, gender=:gender, date_of_birth=:date_of_birth, status=:status, last_update_date=now(), no_pcp=:no_pcp,  first_name_3=:first_name_3, last_name_3=:last_name_3, external_participant=:external_participant"
			+ " WHERE client_id=:client_id AND member_id=:member_id RETURNING *)" + "INSERT INTO comed_participants("
			+ " first_name, last_name, middle_initial, addr1, addr2, city, state, postal_code, gender, date_of_birth, status, created_by, creation_date, no_pcp, client_id, member_id, first_name_3, last_name_3,is_from_file, external_participant)"
			+ "	SELECT :first_name, :last_name, :middle_initial, :addr1, :addr2, :city, :state, :postal_code, :gender, :date_of_birth, :status, :created_by, now(),:no_pcp, :client_id, :member_id,  :first_name_3, :last_name_3, :is_from_file, :external_participant WHERE NOT EXISTS (SELECT * FROM upsert)";

	public static final String UPDATE_PARTICIPANTS = "update comed_participants set status=:status_deleted where client_id=:client_id and id not in (select participant_id from comed_participants_biometrics);" 
			+ "update comed_participants set external_participant = true where status=:status_active and client_id=:client_id and id in (select participant_id from comed_participants_biometrics);";
	
	public static final String INSERT_PARTICIPANT_NAMED_QUERY_SINGLE = "INSERT INTO comed_participants("
			+ " first_name, " + "last_name, " + "middle_initial, " + "addr1, " + "addr2, " + "city, " + "state, "
			+ "postal_code, " + "gender, " + "date_of_birth, " + "status, " + "created_by, " + "creation_date, "
			+ "no_pcp, " + "client_id," + " member_id, " + "first_name_3, " + "last_name_3," + "external_id,"
			+ "is_from_file)" + "	 VALUES (" + ":first_name, " + ":last_name," + ":middle_initial," + ":addr1,"
			+ ":addr2," + ":city," + ":state, " + ":postal_code," + " :gender," + " :date_of_birth," + " :status,"
			+ " :created_by," + " now()," + "false," + " :client_id,"
			+ " ((select count(*) from comed_participants) + 1)," + ":first_name_3," + " :last_name_3,"
			+ " :external_id," + " :is_from_file );";
	public static final String INSERT_PARTICIPANT_NAMED_QUERY_BATCH = "INSERT INTO comed_participants(" + 
			"	id, client_id, first_name, last_name, middle_initial, sufix, addr1, addr2, addr3, city, state, gender, date_of_birth, email_address, phone_number, "+
			"phone_extension, phone_location, member_id, no_pcp, primary_care_physician, past_patient, last_physical_exam, history_heart_disease, history_diabetes, "+
			"history_osteoporosis, history_cancer, cancer_breast, cancer_colon, cancer_lung, cancer_skin, cancer_other, diagnosis_heart_disease, diagnosis_osteoporosis, "+
			"treatment_cholesterol, treatment_diabetes, treatment_hypertension, treatment_osteoporosis, active_smoker, past_smoker, exercise, fruits, grains, smoking, exercise2, "+
			"fruits2, mail_pcp, mail_individual, provider_assist, last_assessment, status, pcp_last_name, pcp_first_name, pcp_middle_name, pcp_academic_degree, "+
			"pcp_addr1, pcp_addr2, pcp_city, pcp_state, pcp_postal_code, created_by, creation_date, last_updated_by, last_update_date, external_id, external_participant, "+
			"postal_code, last_name_3, first_name_3, is_from_file) " + 
			"VALUES (:id, :client_id, :first_name, :last_name, :middle_initial, :sufix, :addr1, :addr2, :addr3, :city, :state, :gender, :date_of_birth, :email_address, :phone_number, "+
			":phone_extension, :phone_location, :member_id, :no_pcp, :primary_care_physician, :past_patient, :last_physical_exam, :history_heart_disease, :history_diabetes, "+
			":history_osteoporosis, :history_cancer, :cancer_breast, :cancer_colon, :cancer_lung, :cancer_skin, :cancer_other, :diagnosis_heart_disease, :diagnosis_osteoporosis, "+
			":treatment_cholesterol, :treatment_diabetes, :treatment_hypertension, :treatment_osteoporosis, :active_smoker, :past_smoker, :exercise, :fruits, :grains, :smoking, :exercise2, "+
			":fruits2, :mail_pcp, :mail_individual, :provider_assist, :last_assessment, :status, :pcp_last_name, :pcp_first_name, :pcp_middle_name, :pcp_academic_degree, "+
			":pcp_addr1, :pcp_addr2, :pcp_city, :pcp_state, :pcp_postal_code, :created_by, :creation_date, :last_updated_by, :last_update_date, :external_id, :external_participant, "+
			":postal_code, :last_name_3, :first_name_3, :is_from_file)";
	public static final String INSERT_CLIENT_ASSESMENT_WITH_UPDATE = "UPDATE comed_client_assessment SET status = false where client_id = :client_id;" + 
			"INSERT INTO " + "comed_client_assessment( client_id, program_id, calendar_year, program_start_date, " + 
			"program_end_date, program_display_name, extended_screenings, created_by, " + 
			"creation_date, last_updated_by,last_update_date, file_name, status, reward_date, marked) " + 
			"VALUES (:client_id, :program_id, :calendar_year, :program_start_date, :program_end_date, :program_display_name, " + 
			":extended_screenings, :created_by, :creation_date, :last_updated_by, :last_update_date, :file_name, :status, :reward_date, :marked);";
	public static final String INSERT_CLIENT_ASSESMENT = "INSERT INTO " + "comed_client_assessment( client_id, program_id, calendar_year, program_start_date, " + 
			"program_end_date, program_display_name, extended_screenings, created_by, " + 
			"creation_date, last_updated_by,last_update_date, file_name, status, reward_date, marked) " + 
			"VALUES (:client_id, :program_id, :calendar_year, :program_start_date, :program_end_date, :program_display_name, " + 
			":extended_screenings, :created_by, :creation_date, :last_updated_by, :last_update_date, :file_name, :status, :reward_date, :marked);";

	public static final String SELECT_LAST_INSERT = "SELECT creation_date,id from comed_participants order by creation_date desc limit 1";
}
