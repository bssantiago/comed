package com.mhc.dao;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.LigthParticipantDTO;
import com.mhc.dto.ParticipantsDTO;
import com.mhc.dto.SearchDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.services.AESService;
import com.mhc.services.AESServiceImpl;
import com.mhc.services.EncryptService;
import com.mhc.util.Constants;
import com.mhc.util.InitUtil;

import java.util.Date;

public class ParticipantDAOImpl extends BaseDAO<ParticipantsDTO> implements ParticipantDAO {
	private static final String EMPTY_STRING = "";
	private static final String BIND_PARTICIPANT_CLIENT = "update comed_participants set client_id = :client_id where id= :participant_id";
	private static final String GET_FILE_QUERY = "select " + "cp.first_name as first_name, " + "cp.gender as gender, "
			+ "cp.last_name as last_name, " + "cp.date_of_birth as date_of_birth, " + "cp.member_id as member_id, "
			+ "cc.vendor as vendor, " + "cc.id as client_id," + "cc.highmark_client_id as highmark_client_id,"
			+ "cc.highmark_site_code as highmark_site_code," + "cc.name as client_name, "
			+ "cpb.cholesterol as cholesterol," + "cpb.fasting as fasting," + "cpb.glucose as glucose,"
			+ "cpb.ldl as ldl," + "cpb.hdl as hdl," + "cpb.triglycerides as triglycerides," + "cpb.height as height,"
			+ "cpb.weight as weight," + "cpb.waist as waist," + "cpb.body_fat as body_fat ," + "cca.marked as marked "
			+ "from comed_participants cp " + "left join comed_clients cc " + "on cp.client_id = cc.id "
			+ "left join comed_client_assessment cca " + "on cc.id = cca.client_id "
			+ "left join comed_participants_biometrics cpb " + "on cp.id = cpb.participant_id "
			+ "where cca.program_id = :program_id and cca.client_id = :client_id";

	private static final String INSERT_PARTICIPANT_NAMED_QUERY = "WITH upsert AS (UPDATE comed_participants SET first_name=:first_name, last_name=:last_name, middle_initial=:middle_initial, addr1=:addr1,"
			+ " addr2=:addr2, city=:city, state=:state, postal_code=:postal_code, gender=:gender, date_of_birth=:date_of_birth, status=:status, last_update_date=now(), no_pcp=:no_pcp,  first_name_3=:first_name_3, last_name_3=:last_name_3"
			+ " WHERE client_id=:client_id AND member_id=:member_id RETURNING *)" + "INSERT INTO comed_participants("
			+ " first_name, last_name, middle_initial, addr1, addr2, city, state, postal_code, gender, date_of_birth, status, created_by, creation_date, no_pcp, client_id, member_id, first_name_3, last_name_3)"
			+ "	SELECT :first_name, :last_name, :middle_initial, :addr1, :addr2, :city, :state, :postal_code, :gender, :date_of_birth, :status, :created_by, now(),:no_pcp, :client_id, :member_id,  :first_name_3, :last_name_3 WHERE NOT EXISTS (SELECT * FROM upsert)";

	public void setParticipant(ParticipantsDTO dto) {
		try {
			HashMap<String, Object> params = participantToNamedParams(dto);
			namedParameterJdbcTemplate.update(INSERT_PARTICIPANT_NAMED_QUERY, params);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}

	public void setParticipantBatch(List<ParticipantsDTO> participants) {
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, Object>[] objs = new HashMap[participants.size()];
			int i = 0;
			for (ParticipantsDTO dto : participants) {
				HashMap<String, Object> params = participantToNamedParams(dto);
				objs[i] = params;
				i++;
			}
			namedParameterJdbcTemplate.batchUpdate(INSERT_PARTICIPANT_NAMED_QUERY, objs);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}

	@Override
	public void bindParticipantWithClient(ParticipantsDTO pdto) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("client_id", pdto.getClient_id());
			params.put("participant_id", pdto.getId());
			namedParameterJdbcTemplate.update(BIND_PARTICIPANT_CLIENT, params);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
		
	}

	public Integer getParticipantByKordinatorId(ParticipantsDTO dto) {
		String query = "SELECT id FROM comed_participants WHERE kordinator_id=:kordinator_id AND client_id=:client_id AND status = 'ACTIVE' LIMIT 1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", dto.getClient_id());
		params.put("kordinator_id", dto.getKordinator_id());
		Integer result = null;
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		if (srs.next()) {
			result = srs.getInt("id");
		}
		return result;
	}

	@Override
	public List<String> getFirstNames(String firstname) {
		AESService aes = new AESServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		firstname = firstname.toLowerCase().substring(0,
				Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, firstname.length()));
		firstname = EncryptService.encryptStringDB(firstname);
		params.put("firstname", "%" + firstname + "%");
		String query = "SELECT DISTINCT first_name FROM comed_participants WHERE first_name_3 like :firstname";
		List<String> firstnames = new ArrayList<String>();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {

			String decrypt = aes.decrypt(InitUtil.getSalt(), srs.getString("first_name"));
			firstnames.add(decrypt);
		}
		return firstnames;
	}

	public File getTxt(Integer client_id, String program_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", client_id);
		params.put("program_id", program_id);
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
		String result = sb.append(vendor).append(tab).append(clientNumber).append(tab).append(siteCode).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("last_name"))).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("first_name"))).append(tab)
				.append((srs.getDate("date_of_birth").toString())).append(tab)
				.append(EncryptService.decryptStringDB(srs.getString("gender"))).append(tab).append("").append(tab)// drawType
				.append(EMPTY_STRING).append(tab) // screeningDate
				.append(EMPTY_STRING).append(tab) // screenType
				.append(((Integer) (srs.getInt("cholesterol"))).toString()).append(tab).append(EMPTY_STRING).append(tab) // fasting
				.append(((Integer) (srs.getInt("glucose"))).toString()).append(tab).append(EMPTY_STRING).append(tab) // sBP
				.append(EMPTY_STRING).append(tab) // dBP
				.append(((Integer) (srs.getInt("ldl"))).toString()).append(((Integer) (srs.getInt("hdl"))).toString())
				.append(((Integer) (srs.getInt("triglycerides"))).toString()).append(EMPTY_STRING).append(tab) // cholesterolHDLRatio
				.append(EMPTY_STRING).append(tab) // hemoglobin
				.append(EMPTY_STRING).append(tab) // cotin
				.append(((Integer) (srs.getInt("height"))).toString()) // wTHeightFeet
				.append(((Integer) (srs.getInt("height"))).toString()) // wTHeightInches
				.append(((Integer) (srs.getInt("weight"))).toString())
				.append(((Integer) (srs.getInt("waist"))).toString()).append(EMPTY_STRING).append(tab) // hRAType
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
	public List<String> getLastNames(String lastname) {
		AESService aes = new AESServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		lastname = lastname.toLowerCase().substring(0,
				Math.min(Constants.MAX_SUBSTRING_LENGHT_ENCRYPTED, lastname.length()));
		lastname = EncryptService.encryptStringDB(lastname);
		params.put("lastname", "%" + lastname + "%");
		String query = "SELECT DISTINCT last_name FROM comed_participants WHERE last_name_3 like :lastname";
		List<String> lastnames = new ArrayList<String>();
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {
			String decrypt = aes.decrypt(InitUtil.getSalt(), srs.getString("last_name"));
			lastnames.add(decrypt);
		}
		return lastnames;
	}

	private HashMap<String, Object> participantToNamedParams(ParticipantsDTO dto) {
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

		String query = "SELECT first_name, last_name, member_id, addr1, addr2, addr3, city, id FROM comed_participants";
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
		String filters = "";
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
			Date param = new Date(dateParam.getYear(), dateParam.getMonth(), dateParam.getDate());
			Timestamp time = new Timestamp(param.getTime());

			params.put("date_of_birth", time);
			filters = filters + "date_of_birth = :date_of_birth AND ";
		}

		if (StringUtils.isNotEmpty(filters)) {
			filters = filters.substring(0, filters.length() - 5);
			filters = " WHERE " + filters;
		}
		return filters;
	}

}
