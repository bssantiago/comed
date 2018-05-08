package com.mhc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;

import com.mhc.dao.ParticipantDAOImpl;
import com.mhc.dto.ClientAssessmentDTO;

public class InitiClientAssessment {

	private static final String FILE_PATH = "C:\\client_assessments-sample.csv";
	private static final int CLIENT_ID_INDEX = 0;
	private static final int CALENDAR_YEAR_INDEX = 1;
	private static final int PROGRAM_START_DATE_INDEX = 2;
	private static final int PROGRAM_END_DATE_INDEX = 3;
	private static final int PROGRAM_DISPLAY_NAME_INDEX = 4;
	private static final int EXTENDED_INDEX = 5;
	private static final int CREATED_BY_INDEX = 6;
	private static final int CREATION_DATE_INDEX = 7;
	private static final int LAST_UPDATED_BY_INDEX = 8;
	private static final int LAST_UPDATE_DATE_INDEX = 9;

	public static void main(String[] args) throws IOException, ParseException {
		List<ClientAssessmentDTO> clientsAssessments = new ArrayList<ClientAssessmentDTO>();
		Reader in = new InputStreamReader(new FileInputStream(FILE_PATH));
		Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
		for (CSVRecord record : records) {
			ClientAssessmentDTO ca = new ClientAssessmentDTO();
			ca.setClient_id(Integer.parseInt(record.get(CLIENT_ID_INDEX)));
			ca.setCalendar_year(Integer.parseInt(record.get(CALENDAR_YEAR_INDEX)));
			ca.setProgram_start_date(
					new SimpleDateFormat(Constants.DATE_FORMAT).parse(record.get(PROGRAM_START_DATE_INDEX)));
			ca.setProgram_end_date(
					new SimpleDateFormat(Constants.DATE_FORMAT).parse(record.get(PROGRAM_END_DATE_INDEX)));
			ca.setProgram_display_name(record.get(PROGRAM_DISPLAY_NAME_INDEX));
			ca.setProgram_id(record.get(PROGRAM_DISPLAY_NAME_INDEX) + " - client " + ca.getClient_id());
			ca.setExtended_screenings(Integer.parseInt(record.get(EXTENDED_INDEX)));
			String createdBy = record.get(CREATED_BY_INDEX);
			String creationDate = record.get(CREATION_DATE_INDEX);
			String lastUpdatedBy = record.get(LAST_UPDATED_BY_INDEX);
			String lastUpdateDate = record.get(LAST_UPDATE_DATE_INDEX);

			ca.setCreated_by(StringUtils.equalsIgnoreCase(createdBy, "NULL") ? null : createdBy);
			ca.setCreation_date(StringUtils.equalsIgnoreCase(creationDate, "NULL") ? Calendar.getInstance().getTime()
					: new SimpleDateFormat(Constants.DATE_FORMAT).parse(creationDate));
			ca.setLast_update_by(StringUtils.equalsIgnoreCase(lastUpdatedBy, "NULL") ? null : lastUpdatedBy);
			ca.setLast_update_date(StringUtils.equalsIgnoreCase(lastUpdateDate, "NULL") ? null
					: new SimpleDateFormat(Constants.DATE_FORMAT).parse(lastUpdateDate));

			ca.setStatus(true);
			ca.setMarked(false);

			clientsAssessments.add(ca);
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
		dao.setClientAssessmentBatch(clientsAssessments);

	}

}
