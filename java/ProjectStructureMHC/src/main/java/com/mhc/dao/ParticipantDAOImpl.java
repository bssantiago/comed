package com.mhc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.ParticipantsDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.util.Constants;

public class ParticipantDAOImpl extends BaseDAO<ParticipantsDTO> implements ParticipantDAO {

	private static final String INSERT_PARTICIPANT = "WITH upsert AS (UPDATE comed_participants SET first_name=:first_name, last_name=:last_name, middle_initial=:middle_initial, addr1=:addr1,"
			+ " addr2=:addr2, city=:city, state=:state, postal_code=:postal_code, gender=:gender, date_of_birth=:date_of_birth, status=:status, last_update_date=now(), no_pcp=:no_pcp,  first_name_3=:first_name_3, last_name_3=:last_name_3"
			+ " WHERE client_id=:client_id AND member_id=:member_id RETURNING *)"
			+ "INSERT INTO comed_participants("
			+ " first_name, last_name, middle_initial, addr1, addr2, city, state, postal_code, gender, date_of_birth, status, created_by, creation_date, no_pcp, client_id, member_id, first_name_3, last_name_3)"
			+ "	SELECT :first_name, :last_name, :middle_initial, :addr1, :addr2, :city, :state, :postal_code, :gender, :date_of_birth, :status, :created_by, now(),:no_pcp, :client_id, :member_id,  :first_name_3, :last_name_3 WHERE NOT EXISTS (SELECT * FROM upsert)";

	public void setParticipant(ParticipantsDTO dto) {
		try {
			HashMap<String,Object> params = participantToNamedParams(dto);
			namedParameterJdbcTemplate.update(INSERT_PARTICIPANT, params);
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
			int i =0;
			for (ParticipantsDTO dto : participants) {
				HashMap<String,Object> params = participantToNamedParams(dto);
				objs[i] = params;
				i++;
			}
			namedParameterJdbcTemplate.batchUpdate(INSERT_PARTICIPANT, objs);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}
	

	@Override
	public List<String> getFirstNames(String firstname) {
		Map<String,Object> params = new HashMap<String,Object>();
	    params.put("firstname", "%" + firstname + "%");
		String query = "SELECT first_name_3 FROM comed_participants WHERE first_name_3 like :firstname";
		List<String> firstnames = new ArrayList<String>();
		SqlRowSet srs= namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {
			firstnames.add(srs.getString("first_name_3"));
		}
		return firstnames;
	}

	@Override
	public List<String> getLastNames(String lastname) {
		Map<String,Object> params = new HashMap<String,Object>();
	    params.put("lastname", "%" + lastname + "%");
		String query = "SELECT last_name_3 FROM comed_participants WHERE last_name_3 like :lastname";
		List<String> lastnames = new ArrayList<String>();
		SqlRowSet srs= namedParameterJdbcTemplate.queryForRowSet(query, params);
		while (srs.next()) {
			lastnames.add(srs.getString("last_name_3"));
		}
		return lastnames;
	}
	
	private HashMap<String,Object> participantToNamedParams(ParticipantsDTO dto) {
		HashMap<String,Object> params = new HashMap<String,Object>();
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
		Object[] obj = new Object[] { dto.getFirst_name(), dto.getLast_name(), dto.getMiddle_initial(),
				dto.getAddr1(), dto.getAddr2(), dto.getCity(), dto.getState(), dto.getPostal_code(),
				dto.getGender(), dto.getDate_of_birth(), Constants.ACTIVE, dto.getNo_pcp(),
				dto.getClient_id(), dto.getMember_id(), dto.getFirst_name(), dto.getLast_name(), dto.getMiddle_initial(),
				dto.getAddr1(), dto.getAddr2(), dto.getCity(), dto.getState(), dto.getPostal_code(),
				dto.getGender(), dto.getDate_of_birth(), Constants.ACTIVE, dto.getCreated_by(), dto.getNo_pcp(),
				dto.getClient_id(), dto.getMember_id() };
		return obj;
	}
	
}
