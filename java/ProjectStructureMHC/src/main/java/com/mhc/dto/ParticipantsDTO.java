package com.mhc.dto;

import java.util.Date;

public class ParticipantsDTO extends BaseParticipantDTO {

	private Integer id;
    // private Integer client_id;
    private String first_name_3;
	private String last_name_3;
    private String middle_initial;
    private String sufix;
    private String  addr1;
    private String  addr2;
    private String  addr3;
    private String  city;
    private String state;
    private String postal_code;
    private String gender;
    private String email_address;
    private String  phone_number;
    private String  phone_extension;
    private String  phone_location;
    private Boolean no_pcp;
    private Integer primary_care_physician;
    private Boolean past_patient;
    private Integer last_physical_exam;
    private Boolean history_heart_disease;
    private Boolean history_diabetes;
    private Boolean history_osteoporosis;
    private Boolean history_cancer;
    private Boolean cancer_breast;
    private Boolean cancer_colon;
    private Boolean cancer_lung;
    private Boolean cancer_skin;
    private Boolean cancer_other;
    private Boolean diagnosis_heart_disease;
    private Boolean diagnosis_osteoporosis;
    private Boolean treatment_cholesterol;
    private Boolean treatment_diabetes;
    private Boolean treatment_hypertension;
    private Boolean treatment_osteoporosis;
    private Boolean active_smoker;
    private Boolean past_smoker;
    private Boolean exercise;
    private Boolean fruits;
    private Boolean grains;
    private Integer smoking;
    private Integer exercise2;
    private Integer fruits2;
    private Boolean mail_pcp;
    private Boolean mail_individual;
    private Boolean provider_assist;
    private String last_assessment;
    private String status;
    private String pcp_last_name;
    private String pcp_first_name;
    private String pcp_middle_name;
    private String pcp_academic_degree;
    private String pcp_addr1;
    private String pcp_addr2;
    private String pcp_city;
    private String pcp_state;
    private String pcp_postal_code;
    private String created_by;
    private Date creation_date;
    private String last_updated_by;
    private Date last_update_date;
    // private String external_id;
    private Boolean external_participant;
    private Boolean is_from_file;
    private String primary_care;
    
    
	public String getFirst_name_3() {
		return first_name_3;
	}
	public void setFirst_name_3(String first_name_3) {
		this.first_name_3 = first_name_3;
	}
	public String getLast_name_3() {
		return last_name_3;
	}
	public void setLast_name_3(String last_name_3) {
		this.last_name_3 = last_name_3;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClient_id() {
		return client_id;
	}
	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public String getMiddle_initial() {
		return middle_initial;
	}
	public void setMiddle_initial(String middle_initial) {
		this.middle_initial = middle_initial;
	}
	public String getSufix() {
		return sufix;
	}
	public void setSufix(String sufix) {
		this.sufix = sufix;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPhone_extension() {
		return phone_extension;
	}
	public void setPhone_extension(String phone_extension) {
		this.phone_extension = phone_extension;
	}
	public String getPhone_location() {
		return phone_location;
	}
	public void setPhone_location(String phone_location) {
		this.phone_location = phone_location;
	}

	public Boolean getNo_pcp() {
		return no_pcp;
	}
	public void setNo_pcp(Boolean no_pcp) {
		this.no_pcp = no_pcp;
	}
	public Integer getPrimary_care_physician() {
		return primary_care_physician;
	}
	public void setPrimary_care_physician(Integer primary_care_physician) {
		this.primary_care_physician = primary_care_physician;
	}
	public Boolean getPast_patient() {
		return past_patient;
	}
	public void setPast_patient(Boolean past_patient) {
		this.past_patient = past_patient;
	}
	public Integer getLast_physical_exam() {
		return last_physical_exam;
	}
	public void setLast_physical_exam(Integer last_physical_exam) {
		this.last_physical_exam = last_physical_exam;
	}
	public Boolean getHistory_heart_disease() {
		return history_heart_disease;
	}
	public void setHistory_heart_disease(Boolean history_heart_disease) {
		this.history_heart_disease = history_heart_disease;
	}
	public Boolean getHistory_diabetes() {
		return history_diabetes;
	}
	public void setHistory_diabetes(Boolean history_diabetes) {
		this.history_diabetes = history_diabetes;
	}
	public Boolean getHistory_osteoporosis() {
		return history_osteoporosis;
	}
	public void setHistory_osteoporosis(Boolean history_osteoporosis) {
		this.history_osteoporosis = history_osteoporosis;
	}
	public Boolean getHistory_cancer() {
		return history_cancer;
	}
	public void setHistory_cancer(Boolean history_cancer) {
		this.history_cancer = history_cancer;
	}
	public Boolean getCancer_breast() {
		return cancer_breast;
	}
	public void setCancer_breast(Boolean cancer_breast) {
		this.cancer_breast = cancer_breast;
	}
	public Boolean getCancer_colon() {
		return cancer_colon;
	}
	public void setCancer_colon(Boolean cancer_colon) {
		this.cancer_colon = cancer_colon;
	}
	public Boolean getCancer_lung() {
		return cancer_lung;
	}
	public void setCancer_lung(Boolean cancer_lung) {
		this.cancer_lung = cancer_lung;
	}
	public Boolean getCancer_skin() {
		return cancer_skin;
	}
	public void setCancer_skin(Boolean cancer_skin) {
		this.cancer_skin = cancer_skin;
	}
	public Boolean getCancer_other() {
		return cancer_other;
	}
	public void setCancer_other(Boolean cancer_other) {
		this.cancer_other = cancer_other;
	}
	public Boolean getDiagnosis_heart_disease() {
		return diagnosis_heart_disease;
	}
	public void setDiagnosis_heart_disease(Boolean diagnosis_heart_disease) {
		this.diagnosis_heart_disease = diagnosis_heart_disease;
	}
	public Boolean getDiagnosis_osteoporosis() {
		return diagnosis_osteoporosis;
	}
	public void setDiagnosis_osteoporosis(Boolean diagnosis_osteoporosis) {
		this.diagnosis_osteoporosis = diagnosis_osteoporosis;
	}
	public Boolean getTreatment_cholesterol() {
		return treatment_cholesterol;
	}
	public void setTreatment_cholesterol(Boolean treatment_cholesterol) {
		this.treatment_cholesterol = treatment_cholesterol;
	}
	public Boolean getTreatment_diabetes() {
		return treatment_diabetes;
	}
	public void setTreatment_diabetes(Boolean treatment_diabetes) {
		this.treatment_diabetes = treatment_diabetes;
	}
	public Boolean getTreatment_hypertension() {
		return treatment_hypertension;
	}
	public void setTreatment_hypertension(Boolean treatment_hypertension) {
		this.treatment_hypertension = treatment_hypertension;
	}
	public Boolean getTreatment_osteoporosis() {
		return treatment_osteoporosis;
	}
	public void setTreatment_osteoporosis(Boolean treatment_osteoporosis) {
		this.treatment_osteoporosis = treatment_osteoporosis;
	}
	public Boolean getActive_smoker() {
		return active_smoker;
	}
	public void setActive_smoker(Boolean active_smoker) {
		this.active_smoker = active_smoker;
	}
	public Boolean getPast_smoker() {
		return past_smoker;
	}
	public void setPast_smoker(Boolean past_smoker) {
		this.past_smoker = past_smoker;
	}
	public Boolean getExercise() {
		return exercise;
	}
	public void setExercise(Boolean exercise) {
		this.exercise = exercise;
	}
	public Boolean getFruits() {
		return fruits;
	}
	public void setFruits(Boolean fruits) {
		this.fruits = fruits;
	}
	public Boolean getGrains() {
		return grains;
	}
	public void setGrains(Boolean grains) {
		this.grains = grains;
	}
	public Integer getSmoking() {
		return smoking;
	}
	public void setSmoking(Integer smoking) {
		this.smoking = smoking;
	}
	public Integer getExercise2() {
		return exercise2;
	}
	public void setExercise2(Integer exercise2) {
		this.exercise2 = exercise2;
	}
	public Integer getFruits2() {
		return fruits2;
	}
	public void setFruits2(Integer fruits2) {
		this.fruits2 = fruits2;
	}
	public Boolean getMail_pcp() {
		return mail_pcp;
	}
	public void setMail_pcp(Boolean mail_pcp) {
		this.mail_pcp = mail_pcp;
	}
	public Boolean getMail_individual() {
		return mail_individual;
	}
	public void setMail_individual(Boolean mail_individual) {
		this.mail_individual = mail_individual;
	}
	public Boolean getProvider_assist() {
		return provider_assist;
	}
	public void setProvider_assist(Boolean provider_assist) {
		this.provider_assist = provider_assist;
	}
	public String getLast_assessment() {
		return last_assessment;
	}
	public void setLast_assessment(String last_assessment) {
		this.last_assessment = last_assessment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPcp_last_name() {
		return pcp_last_name;
	}
	public void setPcp_last_name(String pcp_last_name) {
		this.pcp_last_name = pcp_last_name;
	}
	public String getPcp_first_name() {
		return pcp_first_name;
	}
	public void setPcp_first_name(String pcp_first_name) {
		this.pcp_first_name = pcp_first_name;
	}
	public String getPcp_middle_name() {
		return pcp_middle_name;
	}
	public void setPcp_middle_name(String pcp_middle_name) {
		this.pcp_middle_name = pcp_middle_name;
	}
	public String getPcp_academic_degree() {
		return pcp_academic_degree;
	}
	public void setPcp_academic_degree(String pcp_academic_degree) {
		this.pcp_academic_degree = pcp_academic_degree;
	}
	public String getPcp_addr1() {
		return pcp_addr1;
	}
	public void setPcp_addr1(String pcp_addr1) {
		this.pcp_addr1 = pcp_addr1;
	}
	public String getPcp_addr2() {
		return pcp_addr2;
	}
	public void setPcp_addr2(String pcp_addr2) {
		this.pcp_addr2 = pcp_addr2;
	}
	public String getPcp_city() {
		return pcp_city;
	}
	public void setPcp_city(String pcp_city) {
		this.pcp_city = pcp_city;
	}
	public String getPcp_state() {
		return pcp_state;
	}
	public void setPcp_state(String pcp_state) {
		this.pcp_state = pcp_state;
	}
	public String getPcp_postal_code() {
		return pcp_postal_code;
	}
	public void setPcp_postal_code(String pcp_postal_code) {
		this.pcp_postal_code = pcp_postal_code;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	public String getLast_updated_by() {
		return last_updated_by;
	}
	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}
	public Date getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String kordinator_id) {
		this.external_id = kordinator_id;
	}
	public Boolean getExternal_participant() {
		return external_participant;
	}
	public void setExternal_participant(Boolean external_participant) {
		this.external_participant = external_participant;
	}
	public Boolean getIs_from_file() {
		return is_from_file;
	}
	public void setIs_from_file(Boolean is_from_file) {
		this.is_from_file = is_from_file;
	}
	public String getPrimary_care() {
		return primary_care;
	}
	public void setPrimary_care(String primary_care) {
		this.primary_care = primary_care;
	}
	
	
}
