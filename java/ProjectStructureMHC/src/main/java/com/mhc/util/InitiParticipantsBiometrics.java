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

import com.mhc.dao.BiometricInfoDAOImpl;
import com.mhc.dto.BiometricInfoDTO;

public class InitiParticipantsBiometrics {

	private static final String FILE_PATH = "C:\\assessment_results-sample.csv";

	public enum Header {
		rec_id, client_id, program_id, client_assessment_id, person_id, age, gender, height, weight, systolic, diastolic, fasted, draw_type, no_reaction, triglycerides_range_symbol, triglycerides, cholesterol_range_symbol, cholesterol, hdl_range_symbol, hdl, ldl_range_symbol, ldl, hdl_ratio, framingham_score, chd_risk, body_mass_index, waist_circumference, glucose_range_symbol, glucose, glycohemoglobin, body_fat, t_score, bone_density, cotin, assessment_date, calendar_year, screening_date, pcp_extract_fl, pcp_extract_date, extract_fl, extract_date, reject_fl, created_by, creation_date, last_updated_by, last_update_date
	}

	public static void main(String[] args) throws IOException, ParseException {
		List<BiometricInfoDTO> biometrics = new ArrayList<BiometricInfoDTO>();
		Reader in = new InputStreamReader(new FileInputStream(FILE_PATH));
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Header.class).parse(in);
		BiometricInfoDAOImpl dao = new BiometricInfoDAOImpl();
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
		
		int i = 0;
		for (CSVRecord record : records) {
			if (i > 0) {
				BiometricInfoDTO b = new BiometricInfoDTO();
				b.setBiometric_id(Integer.parseInt(record.get(Header.rec_id)));
				b.setParticipant_id(Integer.parseInt(record.get(Header.person_id)));
				b.setSistolic(parse(Header.systolic, record));
				b.setDiastolic(parse(Header.diastolic, record));
				b.setHeight(parse(Header.height, record));
				b.setWeight(parse(Header.weight, record));
				b.setWaist(parse(Header.waist_circumference, record));
				b.setBody_fat(parse(Header.body_fat, record));
				b.setCholesterol(parse(Header.cholesterol, record));
				b.setHdl(parse(Header.hdl, record));
				b.setTriglycerides(parse(Header.triglycerides, record));
				b.setLdl(parse(Header.ldl, record));
				b.setGlucose(parse(Header.glucose, record));
				b.setHba1c(parse(Header.glycohemoglobin, record));
				b.setTobacco_use(false);
				b.setDuration(new Float(0));
				b.setFasting(Boolean.parseBoolean(record.get(Header.fasted)));
				b.setCreation_date(new SimpleDateFormat(Constants.DATE_FORMAT).parse(record.get(Header.creation_date)));
				b.setDraw_type(record.get(Header.draw_type));
				try {
					dao.saveBiometricInfo(b);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				i++;
			}
			
		}
		

	}

	private static Float parse(Enum<Header> h, CSVRecord record) {
		return Float.parseFloat(record.get(h));
	}
}
