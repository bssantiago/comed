package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mhc.dto.ParticipantsDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.util.Constants;

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
}
