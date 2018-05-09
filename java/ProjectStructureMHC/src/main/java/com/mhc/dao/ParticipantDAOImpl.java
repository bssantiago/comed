package com.mhc.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.LigthParticipantDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.dto.StudyResultDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.services.AESService;
import com.mhc.services.AESServiceImpl;
import com.mhc.services.EncryptService;
import com.mhc.util.Constants;
import com.mhc.util.InitUtil;
import com.mhc.util.PdfUtils;

public class ParticipantDAOImpl extends BaseDAO<ParticipantsDTO> implements ParticipantDAO {
	private static final String EMPTY_STRING = "";
	private static final String BIND_PARTICIPANT_CLIENT = "update comed_participants set external_id = :external_id where id= :participant_id and external_id IS NULL";
	private static final String GET_FILE_QUERY = "select cp.first_name as first_name, cp.gender as gender, "
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
	
	private static final String UPDATE_BIOMETRIC_DOWNLOAD = "UPDATE comed_participants_biometrics " 
			+"SET downloaded = true where participant_id in (select cp.id FROM comed_participants cp  LEFT JOIN comed_client_assessment cca on cp.client_id = cca.client_id " 
			+"where cca.client_id = :client_id and cca.program_id = :program_id)";

	private static final String INSERT_PARTICIPANT_NAMED_QUERY = "WITH upsert AS (UPDATE comed_participants SET first_name=:first_name, last_name=:last_name, middle_initial=:middle_initial, addr1=:addr1,"
			+ " addr2=:addr2, city=:city, state=:state, postal_code=:postal_code, gender=:gender, date_of_birth=:date_of_birth, status=:status, last_update_date=now(), no_pcp=:no_pcp,  first_name_3=:first_name_3, last_name_3=:last_name_3, external_participant=:external_participant"
			+ " WHERE client_id=:client_id AND member_id=:member_id RETURNING *)" + "INSERT INTO comed_participants("
			+ " first_name, last_name, middle_initial, addr1, addr2, city, state, postal_code, gender, date_of_birth, status, created_by, creation_date, no_pcp, client_id, member_id, first_name_3, last_name_3,is_from_file, external_participant)"
			+ "	SELECT :first_name, :last_name, :middle_initial, :addr1, :addr2, :city, :state, :postal_code, :gender, :date_of_birth, :status, :created_by, now(),:no_pcp, :client_id, :member_id,  :first_name_3, :last_name_3, :is_from_file, :external_participant WHERE NOT EXISTS (SELECT * FROM upsert)";

	private static final String UPDATE_PARTICIPANTS = "update comed_participants set status=:status_deleted where client_id=:client_id and id not in (select participant_id from comed_participants_biometrics);" 
			+ "update comed_participants set external_participant = true where status=:status_active and client_id=:client_id and id in (select participant_id from comed_participants_biometrics);";
	
	private static final String INSERT_PARTICIPANT_NAMED_QUERY_SINGLE = "INSERT INTO comed_participants("
			+ " first_name, " + "last_name, " + "middle_initial, " + "addr1, " + "addr2, " + "city, " + "state, "
			+ "postal_code, " + "gender, " + "date_of_birth, " + "status, " + "created_by, " + "creation_date, "
			+ "no_pcp, " + "client_id," + " member_id, " + "first_name_3, " + "last_name_3," + "external_id,"
			+ "is_from_file)" + "	 VALUES (" + ":first_name, " + ":last_name," + ":middle_initial," + ":addr1,"
			+ ":addr2," + ":city," + ":state, " + ":postal_code," + " :gender," + " :date_of_birth," + " :status,"
			+ " :created_by," + " now()," + "false," + " :client_id,"
			+ " ((select count(*) from comed_participants) + 1)," + ":first_name_3," + " :last_name_3,"
			+ " :external_id," + " :is_from_file );";
	private static final String INSERT_PARTICIPANT_NAMED_QUERY_BATCH = "INSERT INTO comed_participants(" + 
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
	private static final String INSERT_CLIENT_ASSESMENT_WITH_UPDATE = "UPDATE comed_client_assessment SET status = false where client_id = :client_id;" + 
			"INSERT INTO " + "comed_client_assessment( client_id, program_id, calendar_year, program_start_date, " + 
			"program_end_date, program_display_name, extended_screenings, created_by, " + 
			"creation_date, last_updated_by,last_update_date, file_name, status, reward_date, marked) " + 
			"VALUES (:client_id, :program_id, :calendar_year, :program_start_date, :program_end_date, :program_display_name, " + 
			":extended_screenings, :created_by, :creation_date, :last_updated_by, :last_update_date, :file_name, :status, :reward_date, :marked);";
	private static final String INSERT_CLIENT_ASSESMENT = "INSERT INTO " + "comed_client_assessment( client_id, program_id, calendar_year, program_start_date, " + 
			"program_end_date, program_display_name, extended_screenings, created_by, " + 
			"creation_date, last_updated_by,last_update_date, file_name, status, reward_date, marked) " + 
			"VALUES (:client_id, :program_id, :calendar_year, :program_start_date, :program_end_date, :program_display_name, " + 
			":extended_screenings, :created_by, :creation_date, :last_updated_by, :last_update_date, :file_name, :status, :reward_date, :marked);";

	private static final String SELECT_LAST_INSERT = "SELECT creation_date,id from comed_participants order by creation_date desc limit 1";

	public Integer setParticipant(ParticipantsDTO dto) {
		Integer result = null;
		try {
			String lastname = dto.getLast_name();
			String name = dto.getFirst_name();
			dto.setExternal_id(dto.getExternal_id());
			dto.setLast_name(EncryptService.encryptStringDB(lastname));
			dto.setFirst_name(EncryptService.encryptStringDB(name));
			dto.setGender(EncryptService.encryptStringDB(dto.getGender()));
			dto.setMiddle_initial(EncryptService.encryptStringDB(""));
			dto.setAddr1(EncryptService.encryptStringDB(""));
			dto.setAddr2(EncryptService.encryptStringDB(""));
			dto.setCity(EncryptService.encryptStringDB(""));
			dto.setState(EncryptService.encryptStringDB(""));
			dto.setPostal_code(EncryptService.encryptStringDB(""));
			dto.setLast_name_3(EncryptService.encryptStringDB(lastname.toLowerCase().substring(0,
					Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, lastname.length()))));
			dto.setFirst_name_3(EncryptService.encryptStringDB(name.toLowerCase().substring(0,
					Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, name.length()))));
			dto.setNo_pcp(false);
			HashMap<String, Object> params = participantToNamedParams(dto, false);
			namedParameterJdbcTemplate.update(INSERT_PARTICIPANT_NAMED_QUERY_SINGLE, params);

			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(SELECT_LAST_INSERT, params);

			if (srs.next()) {
				result = srs.getInt("id");
			}
			return result;

		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void setParticipantBatch(List<ParticipantsDTO> participants, ClientAssessmentDTO clientAssessment) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			HashMap<String, Object> obj = new HashMap<String, Object>();
			obj.put("status_active", Constants.STATUS_ACTIVE);
			obj.put("status_deleted", Constants.STATUS_DELETED);
			obj.put("client_id", clientAssessment.getClient_id());
			
			namedParameterJdbcTemplate.update(UPDATE_PARTICIPANTS, obj);
			
			HashMap<String, Object>[] objs = new HashMap[participants.size()];
			int i = 0;
			for (ParticipantsDTO dto : participants) {
				HashMap<String, Object> params = participantToNamedParams(dto, true);
				objs[i] = params;
				i++;
			}
			namedParameterJdbcTemplate.batchUpdate(INSERT_PARTICIPANT_NAMED_QUERY, objs);

			clientAssessment.setCreation_date(Calendar.getInstance().getTime());
			clientAssessment.setStatus(true);
			clientAssessment.setMarked(false);
			
			HashMap<String, Object> clientAssessmentMap = cilentAssessmentToNamedParam(clientAssessment);
			namedParameterJdbcTemplate.update(INSERT_CLIENT_ASSESMENT_WITH_UPDATE, clientAssessmentMap);
			
			transactionManager.commit(status);
		} catch (DAOSystemException dse) {
			transactionManager.rollback(status);
			throw dse;
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new DAOSystemException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setParticipantBatch(List<ParticipantsDTO> participants) {
		HashMap<String, Object>[] objs = new HashMap[participants.size()];
		int i = 0;
		for (ParticipantsDTO dto : participants) {
			HashMap<String, Object> params = participantCompleteToNamedParams(dto, true);
			objs[i] = params;
			i++;
		}
		namedParameterJdbcTemplate.batchUpdate(INSERT_PARTICIPANT_NAMED_QUERY_BATCH, objs);
	}
	
	@SuppressWarnings("unchecked")
	public void setClientAssessmentBatch(List<ClientAssessmentDTO> clientAssessment) {
		HashMap<String, Object>[] objs = new HashMap[clientAssessment.size()];
		int i = 0;
		for (ClientAssessmentDTO dto : clientAssessment) {
			HashMap<String, Object> params = cilentAssessmentToNamedParam(dto);
			objs[i] = params;
			i++;
		}
		namedParameterJdbcTemplate.batchUpdate(INSERT_CLIENT_ASSESMENT, objs);
	}

	@Override
	public int bindParticipantWithClient(ParticipantsDTO pdto) {
		// TODO
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("external_id", pdto.getExternal_id());
			params.put("participant_id", pdto.getId());
			return namedParameterJdbcTemplate.update(BIND_PARTICIPANT_CLIENT, params);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}

	public Integer getParticipantByExternalId(long client_id, String external_id) {
		String query = "SELECT id FROM comed_participants WHERE external_id=:external_id AND client_id=:client_id AND status=:status LIMIT 1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client_id);
		params.put("external_id", external_id);
		params.put("status", Constants.STATUS_ACTIVE);
		Integer result = null;
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		if (srs.next()) {
			result = srs.getInt("id");
		}
		return result;
	}

	@Override
	public List<String> getFirstNames(String firstname, int clientId) {
		AESService aes = new AESServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		firstname = firstname.toLowerCase().substring(0,
				Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, firstname.length()));
		firstname = EncryptService.encryptStringDB(firstname);
		params.put("firstname", "%" + firstname + "%");
		params.put("status", Constants.STATUS_ACTIVE);
		params.put("client_id", clientId);
		String query = "SELECT DISTINCT first_name FROM comed_participants WHERE first_name_3 like :firstname AND status=:status AND client_id=:client_id";
		List<String> firstnames = new ArrayList<String>();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {

			String decrypt = aes.decrypt(InitUtil.getSalt(), srs.getString("first_name"));
			firstnames.add(decrypt);
		}
		return firstnames;
	}

	@Override
	public File getPdf(BiometricInfoDTO pcb) {
		File file = null;
		try {
			Date creationDate = pcb.getCreation_date();
			Date dob = pcb.getDate_of_birth();
			
			PdfUtils p = new PdfUtils();

			ParticipantsDTO pdto = new ParticipantsDTO();
			pdto.setFirst_name(pcb.getFirst_name());
			pdto.setLast_name(pcb.getLast_name());
			pdto.setDate_of_birth(dob);
			pdto.setCreation_date(creationDate);
			
			List<StudyResultDTO> studies = new ArrayList<StudyResultDTO>();

			String isTobaco = pcb.isTobacco_use() ? "YES" : "NO";
			String isFasting = pcb.isFasting() ? "YES" : "NO";
			studies.add(new StudyResultDTO("Sistolic", "", "" + pcb.getSistolic()));
			studies.add(new StudyResultDTO("Diastolic", "", "" + pcb.getDiastolic()));
			studies.add(new StudyResultDTO("Height", "", "" + pcb.getHeight()));
			studies.add(new StudyResultDTO("Weight", "", "" + pcb.getWeight()));
			studies.add(new StudyResultDTO("Waist", "", "" + pcb.getWaist()));
			studies.add(new StudyResultDTO("Body_fat", "", "" + pcb.getBody_fat()));
			studies.add(new StudyResultDTO("Hdl", "", "" + pcb.getHdl()));
			studies.add(new StudyResultDTO("Ldl", "", "" + pcb.getLdl()));
			studies.add(new StudyResultDTO("Triglycerides", "", "" + pcb.getTriglycerides()));
			studies.add(new StudyResultDTO("Glucose", "", "" + pcb.getGlucose()));
			studies.add(new StudyResultDTO("Tobacco_use", "", isTobaco));
			studies.add(new StudyResultDTO("Fasting", "", isFasting));

			file = p.PdfGenerator(pdto, studies);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public File getTxt(Integer client_id, String program_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client_id);
		params.put("program_id", program_id);
		params.put("status", Constants.STATUS_ACTIVE);
		String vendor = "";
		String clientNumber = "";
		String siteCode = "";
		DateFormat df = new SimpleDateFormat("mmddyyyy_HHmmss");
		Date currentDate = Calendar.getInstance().getTime();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(GET_FILE_QUERY, params);
		File file = new File("csv1.txt");
		try (Writer writer = new BufferedWriter(new FileWriter(file))) {
			String headers = this.getDataHeaders() + System.getProperty("line.separator");
			writer.append(headers);
			while (srs.next()) {
				vendor = srs.getString("vendor");
				clientNumber = srs.getString("highmark_client_id");
				siteCode = srs.getString("highmark_site_code");
				String contents = this.getFileDataRow(srs, vendor, clientNumber, siteCode)
						+ System.getProperty("line.separator");
				writer.append(contents);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileName = vendor + "_BIO_" + clientNumber + "_" + siteCode + "_" + df.format(currentDate) + ".txt";
		File newfile = new File(fileName);
		file.renameTo(newfile);

		return newfile;
	}
	
	public void setDownloadedBiometricInfo(Integer client_id, String program_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client_id);
		params.put("program_id", program_id);
		namedParameterJdbcTemplate.update(UPDATE_BIOMETRIC_DOWNLOAD, params);
	}

	private String getDataHeaders() {
		String[] headers = new String[] { "VendorName", "ClientNumber", "SiteCode", "LastName", "FirstName",
				"BirthDate", "Gender", "UMemberID", "DrawType", "ScreeningDate", "ScreenType", "Cholesterol", "Fasting",
				"BloodGlucose", "SBP", "DBP", "LDL", "HDL", "Triglycerides", "CholesterolHDLRatio", "Hemoglobin",
				"Cotin", "WTHeightFeet", "WTHeightInches", "WTWeight", "WTWaist", "HRAType", "Remarks", "PSA",
				"BoneDensity", "BodyComposition", "Thyroid", "DermaTest" };
		String result = String.join("	", headers);
		return result;
	}

	private String getFileDataRow(SqlRowSet srs, String vendor, String clientNumber, String siteCode) {
		String tab = "	";
		StringBuilder sb = new StringBuilder();
		String result = sb.append(vendor).append(tab)
				.append(clientNumber).append(tab)
				.append(siteCode).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("last_name"))).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("first_name"))).append(tab)
				.append((srs.getDate("date_of_birth").toString())).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("gender"))).append(tab)
				.append(srs.getInt("member_id")).append(tab)
				.append(srs.getString("draw_type")).append(tab)// drawType
				.append(EMPTY_STRING).append(tab) // screeningDate
				.append(EMPTY_STRING).append(tab) // screenType
				.append(((Float) (srs.getFloat("cholesterol"))).toString()).append(tab)
				.append(EMPTY_STRING).append(tab) // fasting
				.append(((Float) (srs.getFloat("glucose"))).toString()).append(tab)
				.append(EMPTY_STRING).append(tab) // sBP
				.append(EMPTY_STRING).append(tab) // dBP
				.append(((Float) (srs.getFloat("ldl"))).toString()).append(tab)
				.append(((Float) (srs.getFloat("hdl"))).toString()).append(tab)
				.append(((Float) (srs.getFloat("triglycerides"))).toString()).append(tab)
				.append(EMPTY_STRING).append(tab) // cholesterolHDLRatio
				.append(EMPTY_STRING).append(tab) // hemoglobin
				.append(EMPTY_STRING).append(tab) // cotin
				.append(((Float) (srs.getFloat("height"))).toString()).append(tab) // wTHeightFeet
				.append(((Float) (srs.getFloat("height"))).toString()).append(tab) // wTHeightInches
				.append(((Float) (srs.getFloat("weight"))).toString()).append(tab)
				.append(((Float) (srs.getFloat("waist"))).toString()).append(tab)
				.append(EMPTY_STRING).append(tab) // hRAType
				.append(EMPTY_STRING).append(tab) // remarks
				.append(EMPTY_STRING).append(tab) // pSA
				.append(EMPTY_STRING).append(tab) // boneDensity
				.append(EMPTY_STRING).append(tab) // bodyComposition
				.append(EMPTY_STRING).append(tab) // thyroid
				.append(EMPTY_STRING) // dermaTest
				.toString();

		return result;
	}

	@Override
	public List<String> getLastNames(String lastname, int clientId) {
		AESService aes = new AESServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		lastname = lastname.toLowerCase().substring(0,
				Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, lastname.length()));
		lastname = EncryptService.encryptStringDB(lastname);
		params.put("lastname", "%" + lastname + "%");
		params.put("status", Constants.STATUS_ACTIVE);
		params.put("client_id", clientId);
		String query = "SELECT DISTINCT last_name FROM comed_participants WHERE last_name_3 like :lastname AND status=:status AND client_id=:client_id";
		List<String> lastnames = new ArrayList<String>();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {
			String decrypt = aes.decrypt(InitUtil.getSalt(), srs.getString("last_name"));
			lastnames.add(decrypt);
		}
		return lastnames;
	}

	private HashMap<String, Object> participantCompleteToNamedParams(ParticipantsDTO dto, boolean fromBatch) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", dto.getId());
		params.put("client_id", dto.getClient_id());
		params.put("member_id", dto.getMember_id());
		params.put("first_name", dto.getFirst_name());
		params.put("last_name", dto.getLast_name());
		params.put("middle_initial", dto.getMiddle_initial());
		params.put("addr1", dto.getAddr1());
		params.put("addr2", dto.getAddr2());
		params.put("city", dto.getCity());
		params.put("state", dto.getState());
		params.put("postal_code", dto.getPostal_code());
		params.put("gender", dto.getGender());
		params.put("date_of_birth", dto.getDate_of_birth());
		params.put("status", dto.getStatus());
		params.put("created_by", dto.getCreated_by());
		params.put("no_pcp", dto.getNo_pcp());
		params.put("member_id", dto.getMember_id());
		params.put("first_name_3", dto.getFirst_name_3());
		params.put("last_name_3", dto.getLast_name_3());
		params.put("is_from_file", fromBatch);
		params.put("sufix", dto.getSufix());
		params.put("addr3", dto.getAddr3());
		params.put("email_address", dto.getEmail_address());
		params.put("phone_number", dto.getPhone_number());
		params.put("phone_extension", dto.getPhone_extension());
		params.put("phone_location", dto.getPhone_location());
		params.put("primary_care_physician", dto.getPrimary_care_physician());
		params.put("past_patient", dto.getPast_patient());
		params.put("last_physical_exam", dto.getLast_physical_exam());
		params.put("history_heart_disease", dto.getHistory_heart_disease());
		params.put("history_diabetes", dto.getHistory_diabetes());
		params.put("history_osteoporosis", dto.getHistory_osteoporosis());
		params.put("history_cancer", dto.getHistory_cancer());
		params.put("cancer_breast", dto.getCancer_breast());
		params.put("cancer_colon", dto.getCancer_colon());
		params.put("cancer_lung", dto.getCancer_lung());
		params.put("cancer_skin", dto.getCancer_lung());
		params.put("cancer_other", dto.getCancer_lung());
		params.put("cancer_lung", dto.getCancer_lung());
		params.put("diagnosis_heart_disease", dto.getDiagnosis_heart_disease());
		params.put("diagnosis_osteoporosis", dto.getDiagnosis_osteoporosis());
		params.put("treatment_cholesterol", dto.getTreatment_cholesterol());
		params.put("treatment_diabetes", dto.getTreatment_diabetes());
		params.put("treatment_hypertension", dto.getTreatment_hypertension());
		params.put("treatment_osteoporosis", dto.getTreatment_osteoporosis());
		params.put("active_smoker", dto.getActive_smoker());
		params.put("past_smoker", dto.getPast_smoker());
		params.put("exercise", dto.getExercise());
		params.put("fruits", dto.getFruits());
		params.put("grains", dto.getGrains());
		params.put("smoking", dto.getSmoking());
		params.put("exercise2", dto.getExercise2());
		params.put("fruits2", dto.getFruits2());
		params.put("mail_pcp", dto.getMail_pcp());
		params.put("mail_individual", dto.getMail_individual());
		params.put("provider_assist", dto.getProvider_assist());
		params.put("last_assessment", dto.getLast_assessment());
		params.put("pcp_last_name", dto.getPcp_last_name());
		params.put("pcp_first_name", dto.getPcp_first_name());
		params.put("pcp_middle_name", dto.getPcp_middle_name());
		params.put("pcp_academic_degree", dto.getPcp_academic_degree());
		params.put("pcp_addr1", dto.getPcp_addr1());
		params.put("pcp_addr2", dto.getPcp_addr2());
		params.put("pcp_city", dto.getPcp_city());
		params.put("pcp_state", dto.getPcp_state());
		params.put("pcp_postal_code", dto.getPcp_postal_code());
		params.put("created_by", dto.getCreated_by());
		params.put("creation_date", dto.getCreation_date());
		params.put("last_updated_by", dto.getLast_updated_by());
		params.put("last_update_date", dto.getLast_update_date());
		params.put("external_id", dto.getExternal_id());
		params.put("external_participant", dto.getExternal_participant());
		params.put("postal_code", dto.getPostal_code());
		params.put("last_name_3", dto.getLast_name_3());
		params.put("first_name_3", dto.getFirst_name_3());
		return params;
	}
	
	private HashMap<String, Object> participantToNamedParams(ParticipantsDTO dto, boolean fromBatch) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", dto.getClient_id());
		params.put("member_id", dto.getMember_id());
		params.put("first_name", dto.getFirst_name());
		params.put("last_name", dto.getLast_name());
		params.put("middle_initial", dto.getMiddle_initial());
		params.put("addr1", dto.getAddr1());
		params.put("addr2", dto.getAddr2());
		params.put("city", dto.getCity());
		params.put("state", dto.getState());
		params.put("postal_code", dto.getPostal_code());
		params.put("gender", dto.getGender());
		params.put("date_of_birth", dto.getDate_of_birth());
		params.put("status", dto.getStatus());
		params.put("created_by", dto.getCreated_by());
		params.put("no_pcp", dto.getNo_pcp());
		params.put("member_id", dto.getMember_id());
		params.put("first_name_3", dto.getFirst_name_3());
		params.put("last_name_3", dto.getLast_name_3());
		params.put("is_from_file", fromBatch);
		params.put("external_id", dto.getExternal_id());
		params.put("external_participant", dto.getExternal_participant());
		return params;
	}
	
	private HashMap<String, Object> cilentAssessmentToNamedParam(ClientAssessmentDTO dto) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", dto.getClient_id());
		params.put("program_id", dto.getProgram_id());
		params.put("calendar_year", dto.getCalendar_year());
		params.put("program_start_date", dto.getProgram_start_date());
		params.put("program_end_date", dto.getProgram_end_date());
		params.put("program_display_name", dto.getProgram_display_name());
		params.put("extended_screenings", dto.getExtended_screenings());
		params.put("created_by", dto.getCreated_by());
		params.put("creation_date", dto.getCreation_date());
		params.put("last_updated_by", dto.getLast_update_by());
		params.put("last_update_date", dto.getLast_update_date());
		params.put("file_name", dto.getFile_name());
		params.put("status", dto.isStatus());
		params.put("reward_date", dto.getReward_date());
		params.put("marked", dto.isMarked());
		
		return params;
	}

	@Override
	protected Object[] toDataObject(ParticipantsDTO dto) {
		Object[] obj = new Object[] { dto.getFirst_name(), dto.getLast_name(), dto.getMiddle_initial(), dto.getAddr1(),
				dto.getAddr2(), dto.getCity(), dto.getState(), dto.getPostal_code(), dto.getGender(),
				dto.getDate_of_birth(), Constants.ACTIVE, dto.getNo_pcp(), dto.getClient_id(), dto.getMember_id(),
				dto.getFirst_name(), dto.getLast_name(), dto.getMiddle_initial(), dto.getAddr1(), dto.getAddr2(),
				dto.getCity(), dto.getState(), dto.getPostal_code(), dto.getGender(), dto.getDate_of_birth(),
				Constants.ACTIVE, dto.getCreated_by(), dto.getNo_pcp(), dto.getClient_id(), dto.getMember_id() };
		return obj;
	}

	public SearchResultDTO<LigthParticipantDTO> search(SearchDTO request) {
		SearchResultDTO<LigthParticipantDTO> result = new SearchResultDTO<LigthParticipantDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limit", request.getPageSize());

		String filters = this.createFilters(request, params);
		String queryCount = "SELECT COUNT(*) as quantity FROM comed_participants" + filters;
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(queryCount, params);
		if (srs.next()) {
			int pages = Integer.parseInt(srs.getString("quantity")) / request.getPageSize() + 1;
			result.setPages(pages);
		}

		String query = "SELECT external_id, first_name, last_name, member_id, addr1, addr2, addr3, city, id FROM comed_participants";
		query = query + filters + " ORDER BY id DESC OFFSET :offset LIMIT :limit";
		int offset = (request.getPage() - 1) * request.getPageSize();
		params.put("offset", offset);
		srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {
			LigthParticipantDTO participant = new LigthParticipantDTO();
			int participant_id = srs.getInt("id");
			String first_name = EncryptService.decryptStringDB(srs.getString("first_name"));
			participant.setFirst_name(first_name);
			String last_name = EncryptService.decryptStringDB(srs.getString("last_name"));
			participant.setParticipant_id(participant_id);
			participant.setLast_name(last_name);
			participant.setMember_id(srs.getString("member_id"));
			participant.setExternal_id(srs.getString("external_id"));
			participant.setAddress(this.getAddress(srs));
			result.getItems().add(participant);
		}
		return result;
	}

	private String getAddress(SqlRowSet srs) {
		String address = EncryptService.decryptStringDB(srs.getString("addr1")) + " "
				+ EncryptService.decryptStringDB(srs.getString("addr2")) + " "
				+ EncryptService.decryptStringDB(srs.getString("addr3")) + ", "
				+ EncryptService.decryptStringDB(srs.getString("city"));
		return address;
	}

	private String createFilters(SearchDTO request, Map<String, Object> params) {
		String filters = "status = :status AND ";
		params.put("status", Constants.STATUS_ACTIVE);
		if ((request.getClient()) != null) {
			params.put("client_id", (request.getClient()));
			filters = filters + "client_id = :client_id AND ";
		}

		if (StringUtils.isNotEmpty(request.getGender())) {
			params.put("gender", EncryptService.encryptStringDB(request.getGender()));
			filters = filters + "gender = :gender AND ";
		}

		if (StringUtils.isNotEmpty(request.getLastname()) && request.getLastname().length() > 2) {
			String lastname = request.getLastname().toLowerCase().substring(0,
					Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, request.getLastname().length()));
			lastname = EncryptService.encryptStringDB(lastname);
			params.put("lastname", "%" + lastname + "%");
			filters = filters + "last_name_3 LIKE :lastname AND ";
		}

		if (StringUtils.isNotEmpty(request.getName()) && request.getName().length() > 2) {
			String name = request.getName().toLowerCase().substring(0,
					Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, request.getName().length()));
			name = EncryptService.encryptStringDB(name);
			params.put("name", "%" + name + "%");
			filters = filters + "first_name_3 LIKE :name AND ";
		}

		if (StringUtils.isNotEmpty(request.getMemberId())) {
			params.put("member_id", request.getMemberId());
			filters = filters + "member_id = :member_id AND ";
		}

		if ((request.getDob() != null)) {
			Date dateParam = request.getDob();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateParam);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DATE);

			filters = filters + "date_of_birth = '" + year + "-" + month + "-" + day + "' AND ";
		}

		if (StringUtils.isNotEmpty(filters)) {
			filters = filters.substring(0, filters.length() - 5);
			filters = " WHERE " + filters;
		}
		return filters;
	}

	public ParticipantsDTO getParticipantFromSP(String client_id, String particiapnt_id) {
		ParticipantsDTO pdto = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client_id);
		params.put("particiapnt_id", particiapnt_id);
		try {
			SqlRowSet srs = this.namedParameterJdbcTemplate
					.queryForRowSet("select * from comed_bio(:client_id,:particiapnt_id)", params);
			if (srs.next()) {
				pdto = new ParticipantsDTO();
				pdto.setFirst_name(EncryptService.decryptStringDB(srs.getString("first_name")));
				pdto.setLast_name(EncryptService.decryptStringDB(srs.getString("last_name")));
				pdto.setDate_of_birth(srs.getDate("date_of_birth"));
				pdto.setAddr1(EncryptService.decryptStringDB(srs.getString("addr1")));

			}

		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

		return pdto;
	}

}
