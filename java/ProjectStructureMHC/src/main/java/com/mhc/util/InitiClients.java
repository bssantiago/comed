package com.mhc.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;

import com.mhc.dao.ClientsDAOImpl;
import com.mhc.dto.ClientDTO;

public class InitiClients {

	private static final String FILE_PATH = "C:\\clients-sample.csv";
	private static final int CLIENT_ID_INDEX = 0;
	private static final int CLIENT_NAME_INDEX = 1;
	private static final int HIGHMARK_CLIENT_ID_INDEX = 2;
	private static final int HIGHTMARK_SITE_CODE_INDEX = 3;
	private static final int CONTACT_NAME_INDEX = 4;
	private static final int CONTACT_PHONE_INDEX = 5;
	private static final int CONTACT_FAX_INDEX = 6;
	private static final int EMAIL_ADDRESS_INDEX = 7;
	private static final int ADDR1_INDEX = 8;
	private static final int ADDR2_INDEX = 9;
	private static final int ADDR3_INDEX = 10;
	private static final int CITY_INDEX = 11;
	private static final int STATE_INDEX = 12;
	private static final int POSTAL_CODE_INDEX = 13;
	private static final int PHYS_LAST_NAME_INDEX = 14;
	private static final int PHYS_FIRST_NAME_INDEX = 15;
	private static final int PHYS_MIDDLE__INDEX = 16;
	private static final int PHYS_ACADEMIC_DEGREE_INDEX = 17;
	private static final int VENDOR_INDEX = 18;
	private static final int CREATED_BY_INDEX = 19;
	private static final int CREATION_DATE_INDEX = 20;
	private static final int LAST_UPDATED_BY_INDEX = 21;
	private static final int LAST_UPDATE_DATE_INDEX = 22;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ParseException {
		List<ClientDTO> clients = new ArrayList<ClientDTO>();
		String line;
		Reader in = new InputStreamReader(new FileInputStream(FILE_PATH));
		BufferedReader bfReader = new BufferedReader(in);
		line = bfReader.readLine();
		String[] columns = line.split(Constants.CSV_COMA_SEPARATOR);
		if (columns.length == 23) {
			while ((line = bfReader.readLine()) != null) {
				columns = line.split(Constants.CSV_COMA_SEPARATOR);
				ClientDTO client = new ClientDTO();
				client.setId(Integer.parseInt(columns[CLIENT_ID_INDEX]));
				client.setName(columns[CLIENT_NAME_INDEX]);
				client.setHighmark_client_id(Integer.parseInt(columns[HIGHMARK_CLIENT_ID_INDEX]));
				client.setHighmark_site_code(Integer.parseInt(columns[HIGHTMARK_SITE_CODE_INDEX]));
				client.setContact_name(columns[CONTACT_NAME_INDEX]);
				client.setContact_phone(columns[CONTACT_PHONE_INDEX]);
				client.setContact_fax(columns[CONTACT_FAX_INDEX]);
				String email = columns[EMAIL_ADDRESS_INDEX];
				client.setEmail_address(StringUtils.equalsIgnoreCase(email, "NULL") ? null : email);
				client.setAddr1(columns[ADDR1_INDEX]);
				client.setAddr2(columns[ADDR2_INDEX]);
				client.setAddr3(columns[ADDR3_INDEX]);
				client.setCity(columns[CITY_INDEX]);
				client.setState(columns[STATE_INDEX]);
				client.setPostal_code(columns[POSTAL_CODE_INDEX]);

				String physLastName = columns[PHYS_LAST_NAME_INDEX];
				String physFirstName = columns[PHYS_FIRST_NAME_INDEX];
				String physMiddleName = columns[PHYS_MIDDLE__INDEX];
				String physAccademicDegree = columns[PHYS_ACADEMIC_DEGREE_INDEX];
				String createdBy = columns[CREATED_BY_INDEX];
				String lastUpdate = columns[LAST_UPDATED_BY_INDEX];
				String lastUpdateDate = columns[LAST_UPDATE_DATE_INDEX];

				client.setPhys_last_name(StringUtils.equalsIgnoreCase(physLastName, "NULL") ? null : physLastName);
				client.setPhys_first_name(StringUtils.equalsIgnoreCase(physFirstName, "NULL") ? null : physFirstName);
				client.setPhys_middle_name(
						StringUtils.equalsIgnoreCase(physMiddleName, "NULL") ? null : physMiddleName);
				client.setPhys_academic_degree(
						StringUtils.equalsIgnoreCase(physAccademicDegree, "NULL") ? null : physAccademicDegree);
				client.setVendor(columns[VENDOR_INDEX]);
				client.setCreated_by(StringUtils.equalsIgnoreCase(createdBy, "NULL") ? null : createdBy);
				client.setCreation_date(
						new SimpleDateFormat(Constants.DATE_FORMAT).parse(columns[CREATION_DATE_INDEX]));
				client.setLast_update_by(StringUtils.equalsIgnoreCase(lastUpdate, "NULL") ? null : lastUpdate);
				client.setLast_update_date(StringUtils.equalsIgnoreCase(lastUpdate, "NULL") ? null
						: new SimpleDateFormat(Constants.DATE_FORMAT).parse(lastUpdateDate));
				clients.add(client);
			}
			ClientsDAOImpl dao = new ClientsDAOImpl();
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
			dao.insertBatchCLients(clients);
		} else {
			System.out.println("missing columns");
		}

	}

}
