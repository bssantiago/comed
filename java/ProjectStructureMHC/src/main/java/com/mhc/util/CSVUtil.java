package com.mhc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mhc.dto.ParticipantsDTO;

public class CSVUtil {
	
	private static final int GROUP_NAME_INDEX = 0;
	private static final int LAST_NAME_INDEX = 1;
	private static final int FIRST_NAME_INDEX = 2;
	private static final int MIDDLE_NAME_INDEX = 3;
	private static final int BIRTH_DATE_INDEX = 4;
	private static final int GENDER_INDEX = 5;
	private static final int RELATIONSHIP_CODE_INDEX = 6;
	private static final int ADDRESS_1_INDEX = 7;
	private static final int ADDRESS_2_INDEX = 8;
	private static final int CITY_INDEX = 9;
	private static final int STATE_INDEX = 10;
	private static final int ZIP_CODE_INDEX = 11;
	private static final int UNIQUE_MEMBER_ID_INDEX = 12;
	private static final int CLIENT_ID_INDEX = 13;
	
	public static List<ParticipantsDTO> csvToParticipant(InputStream uploadedInputStream) throws ParseException, IOException {
		List<ParticipantsDTO> participants = new ArrayList<ParticipantsDTO>();
		String line;
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(uploadedInputStream));
		while ((line = bfReader.readLine()) != null) {
            String[] columns = line.split(Constants.CSV_COMA_SEPARATOR);
            ParticipantsDTO p = new ParticipantsDTO();
            p.setLast_name(columns[LAST_NAME_INDEX]);
            p.setFirst_name(columns[FIRST_NAME_INDEX]);
            p.setMiddle_initial(columns[MIDDLE_NAME_INDEX]);
            p.setDate_of_birth(new SimpleDateFormat(Constants.DATE_FORMAT).parse(columns[BIRTH_DATE_INDEX]));
            p.setGender(columns[GENDER_INDEX]);
            p.setAddr1(columns[ADDRESS_1_INDEX]);
            p.setAddr2(columns[ADDRESS_2_INDEX]);
            p.setCity(columns[CITY_INDEX]);
            p.setState(columns[STATE_INDEX]);
            p.setPostal_code(Integer.parseInt(columns[ZIP_CODE_INDEX]));
            p.setMember_id(columns[UNIQUE_MEMBER_ID_INDEX]);
            p.setClient_id(Integer.parseInt(columns[CLIENT_ID_INDEX]));
            p.setNo_pcp(false);
            participants.add(p);
        }
		return participants;
	}

}
