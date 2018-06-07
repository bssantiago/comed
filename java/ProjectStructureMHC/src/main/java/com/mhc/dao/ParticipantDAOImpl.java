package com.mhc.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
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
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mhc.dao.queries.ParticipantConstants;
import com.mhc.dto.BiometricInfoDTO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.ClientDTO;
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

	private static final Logger LOG = Logger.getLogger(ParticipantDAOImpl.class);

	public Integer setParticipant(ParticipantsDTO dto) {
		Integer result = null;
		try {
			String lastname = dto.getLast_name();
			String name = dto.getFirst_name();
			String gender = dto.getGender();
			/*
			 * if (!StringUtils.isEmpty(dto.getExternal_id())) { ParticipantsDTO spDTO =
			 * getParticipantFromSP(StringUtils.EMPTY, dto.getExternal_id()); if (spDTO !=
			 * null ) { lastname = EncryptService.decryptStringDB(spDTO.getLast_name());
			 * name = EncryptService.decryptStringDB(spDTO.getFirst_name()); gender =
			 * EncryptService.decryptStringDB(spDTO.getGender()); if
			 * (StringUtils.endsWithIgnoreCase(gender, Constants.GENDER_WORD_FEMALE)) {
			 * gender = Constants.GENDER_FEMALE; } else { gender = Constants.GENDER_MALE; }
			 * } }
			 */

			dto.setLast_name(EncryptService.encryptStringDB(lastname));
			dto.setFirst_name(EncryptService.encryptStringDB(name));
			dto.setGender(EncryptService.encryptStringDB(gender));

			dto.setExternal_id(dto.getExternal_id());
			dto.setMiddle_initial(EncryptService.encryptStringDB(StringUtils.EMPTY));
			dto.setAddr1(EncryptService.encryptStringDB(StringUtils.EMPTY));
			dto.setAddr2(EncryptService.encryptStringDB(StringUtils.EMPTY));
			dto.setCity(EncryptService.encryptStringDB(StringUtils.EMPTY));
			dto.setState(EncryptService.encryptStringDB(StringUtils.EMPTY));
			dto.setPostal_code(EncryptService.encryptStringDB(StringUtils.EMPTY));
			dto.setLast_name_3(EncryptService.encryptStringDB(lastname.toLowerCase().substring(0,
					Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, lastname.length()))));
			dto.setFirst_name_3(EncryptService.encryptStringDB(name.toLowerCase().substring(0,
					Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, name.length()))));
			dto.setNo_pcp(false);
			HashMap<String, Object> params = participantToNamedParams(dto, false);
			namedParameterJdbcTemplate.update(ParticipantConstants.INSERT_PARTICIPANT_NAMED_QUERY_SINGLE, params);

			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ParticipantConstants.SELECT_LAST_INSERT, params);

			if (srs.next()) {
				result = srs.getInt("id");
			}
			return result;

		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void setParticipantBatch(List<ParticipantsDTO> participants, ClientAssessmentDTO clientAssessment)
			throws DAOSystemException {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			HashMap<String, Object> obj = new HashMap<String, Object>();
			obj.put("status_active", Constants.STATUS_ACTIVE);
			obj.put("status_deleted", Constants.STATUS_DELETED);
			obj.put("client_id", clientAssessment.getClient_id());

			namedParameterJdbcTemplate.update(ParticipantConstants.UPDATE_PARTICIPANTS, obj);

			HashMap<String, Object>[] objs = new HashMap[participants.size()];
			int i = 0;
			for (ParticipantsDTO dto : participants) {
				HashMap<String, Object> params = participantToNamedParams(dto, true);
				objs[i] = params;
				i++;
			}
			namedParameterJdbcTemplate.batchUpdate(ParticipantConstants.INSERT_PARTICIPANT_NAMED_QUERY, objs);

			clientAssessment.setCreation_date(Calendar.getInstance().getTime());
			clientAssessment.setStatus(true);
			clientAssessment.setMarked(false);

			HashMap<String, Object> clientAssessmentMap = cilentAssessmentToNamedParam(clientAssessment);
			namedParameterJdbcTemplate.update(ParticipantConstants.INSERT_CLIENT_ASSESMENT_WITH_UPDATE,
					clientAssessmentMap);

			transactionManager.commit(status);
		} catch (DuplicateKeyException ex) {
			transactionManager.rollback(status);
			LOG.error(ex);
			throw new DAOSystemException(messageSource.getMessage("error.duplcatekey.clientAssessment", null, null));
		} catch (DAOSystemException dse) {
			transactionManager.rollback(status);
			LOG.error(dse);
			throw new DAOSystemException(messageSource.getMessage(Constants.ERROR_SERVER, null, null));
		} catch (Exception e) {
			transactionManager.rollback(status);
			LOG.error(e);
			throw new DAOSystemException(messageSource.getMessage(Constants.ERROR_SERVER, null, null));
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
		namedParameterJdbcTemplate.batchUpdate(ParticipantConstants.INSERT_PARTICIPANT_NAMED_QUERY_BATCH, objs);
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
		namedParameterJdbcTemplate.batchUpdate(ParticipantConstants.INSERT_CLIENT_ASSESMENT, objs);
	}

	@Override
	public int bindParticipantWithClient(ParticipantsDTO pdto) {
		// TODO
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("external_id", pdto.getExternal_id());
			params.put("participant_id", pdto.getId());
			return namedParameterJdbcTemplate.update(ParticipantConstants.BIND_PARTICIPANT_CLIENT, params);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}

	public Integer getParticipantByExternalId(long client_id, String external_id) {
		String query = "SELECT id FROM comed_participants WHERE external_id=:external_id AND client_id=:client_id AND (status=:status OR status is NULL) LIMIT 1";
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
		String query = "SELECT DISTINCT first_name FROM comed_participants WHERE first_name_3 like :firstname AND (status=:status OR status is NULL) AND client_id=:client_id";
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
			Date creationDate = pcb.getCreate_date();
			Date dob = pcb.getDate_of_birth();

			PdfUtils p = new PdfUtils();

			ParticipantsDTO pdto = new ParticipantsDTO();
			pdto.setFirst_name(pcb.getFirst_name());
			pdto.setLast_name(pcb.getLast_name());
			pdto.setDate_of_birth(dob);
			pdto.setCreation_date(creationDate);

			List<StudyResultDTO> studies = new ArrayList<StudyResultDTO>();


			studies.add(new StudyResultDTO("Total Cholesterol", "Below 200", "" + Math.round(pcb.getCholesterol())));
			String bloodPressure = Math.round(pcb.getSistolic()) + "/"
					+ Math.round(pcb.getDiastolic());
			
			String aux = Double.toString(pcb.getHeight());
			String[] aux2= StringUtils.split(aux, ".");
			
			Double first = Double.parseDouble(aux2[0]); 
			Double second = (aux2.length == 1) ? 0 :  Double.parseDouble(aux2[1]);
			
			double height2 = Math.sqrt((first * 12) + second);
			
			double weight = pcb.getWeight();
			double bmi = 0;
			if (height2 != 0) {
				bmi = this.round((weight / height2) * 703, ParticipantConstants.DECIMAL_PLACES);
			}
			studies.add(new StudyResultDTO("Triglycerides", "Below 150",
					"" + Math.round(pcb.getTriglycerides())) );
			studies.add(new StudyResultDTO("HDL Cholesterol", "Above 40 male/50 female",
					"" + Math.round(pcb.getHdl())));
			studies.add(new StudyResultDTO("LDL Cholesterol", "Below 100",
					"" + Math.round(pcb.getLdl())));
			studies.add(new StudyResultDTO("Fasting Glucose", "Below 100",
					"" + Math.round(pcb.getGlucose())));
			studies.add(new StudyResultDTO("Blood Pressure", "120/80", bloodPressure));
			studies.add(new StudyResultDTO("Body Mass Index", "Below 25", Double.toString(bmi)));

			file = p.PdfGenerator(pdto, studies);

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return file;
	}

	public File getTxt(String program_id, ClientDTO client) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client.getId());
		params.put("program_id", program_id);
		params.put("status", Constants.STATUS_ACTIVE);
		String vendor = client.getVendor();
		String clientNumber = Integer.toString(client.getHighmark_client_id());
		String siteCode = Integer.toString(client.getHighmark_site_code());
		DateFormat df = new SimpleDateFormat("mmddyyyy_HHmmss");
		Date currentDate = Calendar.getInstance().getTime();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ParticipantConstants.GET_FILE_QUERY, params);
		File file = new File("csv1.txt");
		String fileName = vendor + "_BIO_" + clientNumber + "_" + siteCode + "_" + df.format(currentDate) + ".txt";
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

		File newfile = new File(fileName);
		file.renameTo(newfile);

		return newfile;
	}

	public void setDownloadedBiometricInfo(Integer client_id, String program_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client_id);
		params.put("program_id", program_id);
		namedParameterJdbcTemplate.update(ParticipantConstants.UPDATE_BIOMETRIC_DOWNLOAD, params);
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

	private double round(double d, int decimalPlace) {
		return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private String getFileDataRow(SqlRowSet srs, String vendor, String clientNumber, String siteCode) {
		String tab = "	";
		StringBuilder sb = new StringBuilder();
		String result = sb.append(vendor).append(tab).append(clientNumber).append(tab).append(siteCode).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("last_name"))).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("first_name"))).append(tab)
				.append((srs.getDate("date_of_birth").toString())).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("gender"))).append(tab)
				.append(srs.getInt("member_id")).append(tab).append(srs.getString("draw_type")).append(tab)// drawType
				.append(StringUtils.EMPTY).append(tab) // screeningDate
				.append(StringUtils.EMPTY).append(tab) // screenType
				.append(((Float) (srs.getFloat("cholesterol"))).toString()).append(tab).append(StringUtils.EMPTY)
				.append(tab) // fasting
				.append(((Float) (srs.getFloat("glucose"))).toString()).append(tab).append(StringUtils.EMPTY)
				.append(tab) // sBP
				.append(StringUtils.EMPTY).append(tab) // dBP
				.append(((Float) (srs.getFloat("ldl"))).toString()).append(tab)
				.append(((Float) (srs.getFloat("hdl"))).toString()).append(tab)
				.append(((Float) (srs.getFloat("triglycerides"))).toString()).append(tab).append(StringUtils.EMPTY)
				.append(tab) // cholesterolHDLRatio
				.append(StringUtils.EMPTY).append(tab) // hemoglobin
				.append(StringUtils.EMPTY).append(tab) // cotin
				.append(((Float) (srs.getFloat("height"))).toString()).append(tab) // wTHeightFeet
				.append(((Float) (srs.getFloat("height"))).toString()).append(tab) // wTHeightInches
				.append(((Float) (srs.getFloat("weight"))).toString()).append(tab)
				.append(((Float) (srs.getFloat("waist"))).toString()).append(tab).append(StringUtils.EMPTY).append(tab) // hRAType
				.append(StringUtils.EMPTY).append(tab) // remarks
				.append(StringUtils.EMPTY).append(tab) // pSA
				.append(StringUtils.EMPTY).append(tab) // boneDensity
				.append(StringUtils.EMPTY).append(tab) // bodyComposition
				.append(StringUtils.EMPTY).append(tab) // thyroid
				.append(StringUtils.EMPTY) // dermaTest
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
		String query = "SELECT DISTINCT last_name FROM comed_participants WHERE last_name_3 like :lastname AND (status=:status OR status is NULL) AND client_id=:client_id";
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

		String query = "SELECT external_id, first_name, last_name, member_id, addr1, addr2, addr3, city, id, date_of_birth FROM comed_participants";
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
			participant.setDate_of_birth(srs.getDate("date_of_birth"));
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
			String gender = request.getGender();
			String genderWord;
			if (StringUtils.equalsIgnoreCase(gender, Constants.GENDER_FEMALE)) {
				genderWord = Constants.GENDER_WORD_FEMALE;
			} else {
				genderWord = Constants.GENDER_WORD_MALE;
			}
			params.put("gender", EncryptService.encryptStringDB(gender));
			params.put("gender_word", EncryptService.encryptStringDB(genderWord));
			filters = filters + "(gender = :gender OR gender = :gender_word) AND ";
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
		params.put("particiapnt_id", Integer.parseInt(particiapnt_id));
		try {
			SqlRowSet srs = this.namedParameterJdbcTemplate
					.queryForRowSet("select * from get_ahn_patient(:particiapnt_id)", params);
			if (srs.next()) {
				pdto = new ParticipantsDTO();
				pdto.setFirst_name(srs.getString("patient_first_name"));
				pdto.setLast_name(srs.getString("patient_last_name"));
				pdto.setDate_of_birth(srs.getDate("patient_birth_date"));
				pdto.setGender(srs.getString("gender"));
			}

		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}

		return pdto;
	}

}
