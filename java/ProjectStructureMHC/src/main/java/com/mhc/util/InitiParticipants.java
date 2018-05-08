package com.mhc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.classfile.Constant;

import com.mhc.dao.ParticipantDAOImpl;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.services.EncryptService;

public class InitiParticipants {

	private static final String FILE_PATH = "C:\\participants-sample.csv";
	private static final String NULL = "NULL";
	public enum Header {
		person_id, client_id, first_name, last_name, middle_initial, suffix, addr1, addr2, addr3, city, state, postal_code, gender, date_of_birth, email_address, phone_number, phone_extension, phone_location, member_id, no_pcp, primary_care_physician, past_patient, last_physical_exam, history_heart_disease, history_diabetes, history_osteoporosis, history_cancer, cancer_breast, cancer_colon, cancer_lung, cancer_skin, cancer_other, diagnosis_heart_disease, diagnosis_osteoporosis, treatment_cholesterol, treatment_diabetes, treatment_hypertension, treatment_osteoporosis, active_smoker, past_smoker, exercise, fruits, grains, smoking, exercise2, fruits2, mail_pcp, mail_individual, provider_assist, last_assessment, status, pcp_last_name, pcp_first_name, pcp_middle_name, pcp_academic_degree, pcp_addr1, pcp_addr2, pcp_city, pcp_state, pcp_postal_code, created_by, creation_date, last_updated_by, last_update_date
	}
	public static void main(String[] args) throws IOException, ParseException {
		String salt = "1234567887654321";
		List<ParticipantsDTO> participants = new ArrayList<ParticipantsDTO>();
		Reader in = new InputStreamReader(new FileInputStream(FILE_PATH));
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Header.class).parse(in);
		int i = 0;
		for (CSVRecord record : records) {
			if (i > 0) {
				ParticipantsDTO p = new ParticipantsDTO();
				String name = record.get(Header.first_name);
				String lastName = record.get(Header.last_name);
				p.setId(Integer.parseInt(record.get(Header.person_id)));
				p.setClient_id(Integer.parseInt(record.get(Header.client_id)));
				p.setFirst_name(EncryptService.encryptString(name,salt));
				p.setLast_name(EncryptService.encryptString(lastName,salt));
				p.setLast_name_3(EncryptService.encryptString(lastName.toLowerCase().substring(0,
						Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, lastName.length())), salt));
				p.setFirst_name_3(EncryptService.encryptString(name.toLowerCase().substring(0,
						Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, name.length())), salt));
				String middleInitial = record.get(Header.middle_initial);
				p.setMiddle_initial(StringUtils.equalsIgnoreCase(middleInitial, NULL)? null: EncryptService.encryptString(middleInitial,salt));
				String suffix = record.get(Header.suffix);
				p.setSufix(StringUtils.equalsIgnoreCase(suffix, NULL) ? null: EncryptService.encryptString(suffix,salt));
				p.setAddr1(EncryptService.encryptString(record.get(Header.addr1),salt));
				p.setAddr2(EncryptService.encryptString(record.get(Header.addr2),salt));
				p.setAddr3(EncryptService.encryptString(record.get(Header.addr3),salt));
				p.setCity(EncryptService.encryptString(record.get(Header.city),salt));
				p.setState(EncryptService.encryptString(record.get(Header.state),salt));
				p.setPostal_code(EncryptService.encryptString(record.get(Header.postal_code),salt));
				p.setGender(EncryptService.encryptString(record.get(Header.gender),salt));
				p.setDate_of_birth(new SimpleDateFormat(Constants.DATE_FORMAT).parse(record.get(Header.date_of_birth)));
				String email = record.get(Header.email_address);
				p.setEmail_address(StringUtils.equalsIgnoreCase(email, NULL) || StringUtils.isBlank(email) ? null : EncryptService.encryptString(email,salt));
				String phone = record.get(Header.phone_number);
				p.setPhone_number(StringUtils.equalsIgnoreCase(phone, NULL) || StringUtils.isBlank(phone) ? null: EncryptService.encryptString(phone,salt));
				String phoneExtention = record.get(Header.phone_extension);
				p.setPhone_extension(StringUtils.equalsIgnoreCase(phoneExtention, NULL) || StringUtils.isBlank(phoneExtention) ? null: EncryptService.encryptString(phoneExtention,salt));
				String phoneLocation = record.get(Header.phone_location);
				p.setPhone_location(StringUtils.equalsIgnoreCase(phoneLocation, NULL) || StringUtils.isBlank(phoneLocation) ? null: EncryptService.encryptString(phoneLocation,salt));
				p.setMember_id(record.get(Header.member_id));
				p.setNo_pcp(Boolean.parseBoolean(record.get(Header.no_pcp)));
				p.setPrimary_care_physician(Integer.parseInt(record.get(Header.primary_care_physician)));
				p.setPast_patient(Boolean.parseBoolean(record.get(Header.past_patient)));
				String lastPhysicalExam = record.get(Header.last_physical_exam);
				p.setLast_physical_exam(StringUtils.equalsIgnoreCase(lastPhysicalExam, NULL) ? null : Integer.parseInt(lastPhysicalExam));
				p.setHistory_heart_disease(Boolean.parseBoolean(record.get(Header.history_heart_disease)));
				p.setHistory_diabetes(Boolean.parseBoolean(record.get(Header.history_diabetes)));
				String historyOsteoporosis = record.get(Header.history_osteoporosis);
				p.setHistory_osteoporosis(StringUtils.equalsIgnoreCase(historyOsteoporosis, NULL)? null: Boolean.parseBoolean(historyOsteoporosis));
				p.setHistory_cancer(Boolean.parseBoolean(record.get(Header.history_cancer)));
				p.setCancer_breast(Boolean.parseBoolean(record.get(Header.cancer_breast)));
				p.setCancer_colon(Boolean.parseBoolean(record.get(Header.cancer_colon)));
				p.setCancer_lung(Boolean.parseBoolean(record.get(Header.cancer_lung)));
				p.setCancer_skin(Boolean.parseBoolean(record.get(Header.cancer_skin)));
				p.setCancer_other(Boolean.parseBoolean(record.get(Header.cancer_other)));
				p.setDiagnosis_heart_disease(Boolean.parseBoolean(record.get(Header.diagnosis_heart_disease)));
				String diagnosisOsteoporosis = record.get(Header.diagnosis_osteoporosis);
				p.setDiagnosis_osteoporosis(StringUtils.equalsIgnoreCase(diagnosisOsteoporosis, NULL)? null : Boolean.parseBoolean(diagnosisOsteoporosis));
				p.setTreatment_cholesterol(Boolean.parseBoolean(record.get(Header.treatment_cholesterol)));
				p.setTreatment_diabetes(Boolean.parseBoolean(record.get(Header.treatment_diabetes)));
				p.setTreatment_hypertension(Boolean.parseBoolean(record.get(Header.treatment_hypertension)));
				String tratementOsteoporosis = record.get(Header.treatment_osteoporosis);
				p.setTreatment_osteoporosis(StringUtils.equals(tratementOsteoporosis, NULL)? null: Boolean.parseBoolean(tratementOsteoporosis));
				p.setActive_smoker(Boolean.parseBoolean(record.get(Header.active_smoker)));
				p.setPast_smoker(Boolean.parseBoolean(record.get(Header.past_smoker)));
				p.setExercise(Boolean.parseBoolean(record.get(Header.exercise)));
				p.setFruits(Boolean.parseBoolean(record.get(Header.fruits)));
				p.setGrains(Boolean.parseBoolean(record.get(Header.grains)));
				p.setSmoking(Integer.parseInt(record.get(Header.smoking)));
				p.setExercise2(Integer.parseInt(record.get(Header.exercise2)));
				p.setFruits2(Integer.parseInt(record.get(Header.fruits2)));
				String mailPCP = record.get(Header.mail_pcp);
				p.setMail_pcp(StringUtils.equalsIgnoreCase(mailPCP, NULL)? null: Boolean.parseBoolean(mailPCP));
				String mailIndividual = record.get(Header.mail_individual);
				p.setMail_individual(StringUtils.equalsIgnoreCase(mailIndividual, NULL)? null: Boolean.parseBoolean(mailIndividual));
				p.setProvider_assist(Boolean.parseBoolean(record.get(Header.provider_assist)));
				String lastAssessment = record.get(Header.last_assessment);
				p.setLast_assessment(StringUtils.equalsIgnoreCase(lastAssessment, NULL)? null:lastAssessment);
				//String status = record.get(Header.status);
				//p.setStatus(StringUtils.equalsIgnoreCase(status, NULL)? null:status);
				p.setStatus(Constants.STATUS_ACTIVE);
				p.setPcp_last_name(record.get(Header.pcp_last_name));
				p.setPcp_first_name(record.get(Header.pcp_first_name));
				String pcpMiddleName = record.get(Header.pcp_middle_name);
				p.setPcp_middle_name(StringUtils.equalsIgnoreCase(pcpMiddleName, NULL)? null:pcpMiddleName);
				String pcpAcademicDegree = record.get(Header.pcp_academic_degree);
				p.setPcp_academic_degree(StringUtils.equalsIgnoreCase(pcpAcademicDegree, NULL)? null:pcpAcademicDegree);
				p.setPcp_addr1(record.get(Header.pcp_addr1));
				String pcpAddr2 = record.get(Header.pcp_addr2);
				p.setPcp_addr2(StringUtils.equalsIgnoreCase(pcpAddr2, NULL) || StringUtils.isBlank(pcpAddr2)? null:pcpAddr2);
				p.setPcp_city(record.get(Header.pcp_city));
				String pcpState = record.get(Header.pcp_state);
				p.setPcp_state(StringUtils.equalsIgnoreCase(pcpState, NULL)? null: pcpState );
				String pcpPostalCode = record.get(Header.pcp_postal_code);
				p.setPcp_postal_code(StringUtils.equalsIgnoreCase(pcpPostalCode, NULL) ? null: pcpPostalCode);
				String createdBy = record.get(Header.created_by);
				p.setCreated_by(StringUtils.equalsIgnoreCase(createdBy, NULL)?  null: createdBy );
				p.setCreation_date(new SimpleDateFormat(Constants.DATE_FORMAT).parse(record.get(Header.creation_date)));
				String lastUpdateBy = record.get(Header.last_updated_by);
				p.setLast_updated_by(StringUtils.equalsIgnoreCase(lastUpdateBy, NULL)?  null: lastUpdateBy );
				String lastUpdateDate = record.get(Header.last_update_date);
				p.setLast_update_date(StringUtils.equalsIgnoreCase(lastUpdateDate, NULL)?  null:new SimpleDateFormat(Constants.DATE_FORMAT).parse(lastUpdateDate) );
				participants.add(p);
			} else {
				i++;
			}
			
		}
		ParticipantDAOImpl dao = new ParticipantDAOImpl();
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/comed");
		ds.setUsername("postgres");
		ds.setPassword("toor");
		ds.setInitialSize(10);
		ds.setMinIdle(10);
		ds.setMaxActive(100);
		ds.setMaxWait(10000);
		ds.setTestOnBorrow(true);
		ds.setTestWhileIdle(true);
		ds.setTestOnReturn(false);

		ds.setTimeBetweenEvictionRunsMillis(5000);
		ds.setMinEvictableIdleTimeMillis(30000);
		ds.setNumTestsPerEvictionRun(4);

		ds.setRemoveAbandonedTimeout(60);
		ds.setRemoveAbandoned(true);
		ds.setLogAbandoned(true);
		ds.setDefaultAutoCommit(true);
		dao.setDataSource(ds);
		dao.setParticipantBatch(participants);

	}

}
