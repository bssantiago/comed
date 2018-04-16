package com.mhc.dao;

import java.util.ArrayList;
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

public class ParticipantDAOImpl extends BaseDAO<ParticipantsDTO> implements ParticipantDAO {

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
		if(srs.next()) {
			int pages = Integer.parseInt(srs.getString("quantity")) / request.getPageSize() + 1;
			result.setPages(pages);
		}
				
		String query = "SELECT first_name, last_name, member_id, addr1, addr2, addr3, city FROM comed_participants";
		query = query + filters + " ORDER BY id DESC OFFSET :offset LIMIT :limit";
		int offset = (request.getPage()-1) * request.getPageSize();
		params.put("offset", offset);
		srs = namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {
			LigthParticipantDTO participant = new LigthParticipantDTO();
			String first_name = EncryptService.decryptStringDB(srs.getString("first_name"));
			participant.setFirst_name(first_name);
			String last_name = EncryptService.decryptStringDB(srs.getString("last_name"));
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
		if (StringUtils.isNotEmpty(request.getClient())) {
			params.put("client_id", EncryptService.encryptStringDB(request.getClient()));
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

		if (StringUtils.isNotEmpty(filters)) {
			filters = filters.substring(0, filters.length() - 5);
			filters = " WHERE " + filters;
		}
		return filters;
	}

}
