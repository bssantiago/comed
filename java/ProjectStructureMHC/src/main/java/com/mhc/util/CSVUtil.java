package com.mhc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.mhc.dto.ParticipantsDTO;
import com.mhc.exceptions.ParseCSVException;
import com.mhc.services.EncryptService;

public class CSVUtil {

	private static final int MAX_COLUMNS = 14;
	private enum EligibilityHeader {
		GROUP_NAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME, BIRTH_DATE, GENDER, RELATIONSHIP_CODE, ADDRESS_1, ADDRESS_2, CITY, STATE, ZIP_CODE, UNIQUE_MEMBER_ID, CLIENT_NUMBER
	}

	public static List<ParticipantsDTO> csvToParticipants(int client_id, InputStream uploadedInputStream)
			throws ParseCSVException, IOException, ParseException {
		Reader in = new InputStreamReader(uploadedInputStream);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(EligibilityHeader.class).parse(in);
		int i = 0;
		List<ParticipantsDTO> participants = new ArrayList<ParticipantsDTO>();
		for (CSVRecord record : records) {
			if (i > 0) {
				ParticipantsDTO p = new ParticipantsDTO();
				String name = record.get(EligibilityHeader.FIRST_NAME);
				String lastName = record.get(EligibilityHeader.LAST_NAME);
				p.setLast_name(EncryptService.encryptStringDB(lastName));
				p.setFirst_name(EncryptService.encryptStringDB(name));
				p.setMiddle_initial(EncryptService.encryptStringDB(record.get(EligibilityHeader.MIDDLE_NAME)));
				p.setDate_of_birth(new SimpleDateFormat(Constants.DATE_FORMAT).parse(record.get(EligibilityHeader.BIRTH_DATE)));
				p.setGender(EncryptService.encryptStringDB(record.get(EligibilityHeader.GENDER)));
				p.setAddr1(EncryptService.encryptStringDB(record.get(EligibilityHeader.ADDRESS_1)));
				p.setAddr2(EncryptService.encryptStringDB(record.get(EligibilityHeader.ADDRESS_2)));
				p.setCity(EncryptService.encryptStringDB(record.get(EligibilityHeader.CITY)));
				p.setState(EncryptService.encryptStringDB(record.get(EligibilityHeader.STATE)));
				p.setPostal_code(EncryptService.encryptStringDB(record.get(EligibilityHeader.ZIP_CODE)));
				p.setMember_id(record.get(EligibilityHeader.UNIQUE_MEMBER_ID));
				p.setClient_id(client_id);
				p.setLast_name_3(EncryptService.encryptStringDB(lastName.toLowerCase().substring(0,
						Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, lastName.length()))));
				p.setFirst_name_3(EncryptService.encryptStringDB(name.toLowerCase().substring(0,
						Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, name.length()))));
				p.setNo_pcp(false);
				p.setStatus(Constants.ACTIVE);
				p.setExternal_participant(false);
				participants.add(p);
			} else {
				if (record.size() != MAX_COLUMNS) {
					throw new ParseCSVException();
				}
				i++;
			}
		}
		return participants;

	}
}
