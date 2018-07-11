package com.mhc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private static final String regex = "^[0-1]?[0-9]/[0-3]?[0-9]/[0-9][0-9][0-9][0-9]$";

	public static List<ParticipantsDTO> csvToParticipants(int client_id,int clientHightMark, InputStream uploadedInputStream)
			throws ParseCSVException, IOException, ParseException {
		Reader in = new InputStreamReader(uploadedInputStream);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(EligibilityHeader.class).parse(in);
		int i = 0;
		int clientNumber = 0;
		List<ParticipantsDTO> participants = new ArrayList<ParticipantsDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		sdf.setLenient(false);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		for (CSVRecord record : records) {
			if (i > 0) {
				clientNumber = Integer.parseInt(record.get(EligibilityHeader.CLIENT_NUMBER));
				if (clientNumber == clientHightMark) {
					ParticipantsDTO p = new ParticipantsDTO();
					String name = record.get(EligibilityHeader.FIRST_NAME);
					String lastName = record.get(EligibilityHeader.LAST_NAME);
					p.setLast_name(EncryptService.encryptStringDB(lastName));
					p.setFirst_name(EncryptService.encryptStringDB(name));
					p.setMiddle_initial(EncryptService.encryptStringDB(record.get(EligibilityHeader.MIDDLE_NAME)));
					String dobString = record.get(EligibilityHeader.BIRTH_DATE);
					matcher = pattern.matcher(dobString);
					if (!matcher.matches()) {
						throw new ParseException("invalid date format: " + dobString , 0);
					}
					Date dob = sdf.parse(dobString);					
					p.setDate_of_birth(dob);
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
					participants.clear();
					break;
				}				
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
