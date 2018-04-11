package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.ParticipantsDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.services.AESService;
import com.mhc.services.AESServiceImpl;
import com.mhc.util.Constants;
import com.mhc.util.InitUtil;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class ParticipantDAOImpl extends BaseDAO<ParticipantsDTO> implements ParticipantDAO {

	private static final String INSERT_PARTICIPANT = "WITH upsert AS (UPDATE comed_participants SET first_name=?, last_name=?, middle_initial=?, addr1=?,"
			+ " addr2=?, city=?, state=?, postal_code=?, gender=?, date_of_birth=?, status=?, last_update_date=now(), no_pcp=?"
			+ " WHERE client_id=? AND member_id=? RETURNING *)"
			+ "INSERT INTO comed_participants("
			+ " first_name, last_name, middle_initial, addr1, addr2, city, state, postal_code, gender, date_of_birth, status, created_by, creation_date, no_pcp, client_id, member_id)"
			+ "	SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),?, ?, ? WHERE NOT EXISTS (SELECT * FROM upsert)";

	public void setParticipant(ParticipantsDTO dto) {
		try {
			dto.setCreation_date(Calendar.getInstance().getTime());
			Object[] obj = this.toDataObject(dto);
			jdbcTemplate.update(INSERT_PARTICIPANT, obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}

	public void setParticipantBatch(List<ParticipantsDTO> participants) {
		try {

			List<Object[]> objs = new ArrayList<Object[]>();
			for (ParticipantsDTO dto : participants) {
				dto.setCreation_date(Calendar.getInstance().getTime());
				Object[] obj = this.toDataObject(dto);
				objs.add(obj);
			}
			jdbcTemplate.batchUpdate(INSERT_PARTICIPANT, objs);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
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
}
