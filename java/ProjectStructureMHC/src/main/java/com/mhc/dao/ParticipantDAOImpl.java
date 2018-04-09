package com.mhc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mhc.dto.ParticipantsDTO;
import com.mhc.exceptions.dao.DAOSystemException;
import com.mhc.util.Constants;

public class ParticipantDAOImpl extends BaseDAO implements ParticipantDAO {

	private static final String INSERT_PARTICIPANT = "INSERT INTO public.comed_participants(" + 
			" client_id, first_name, last_name, middle_initial, addr1, addr2, city, state, postal_code, gender, date_of_birth, member_id, status, created_by, creation_date, no_pcp)" + 
			"	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),?)";


	public void setParticipant(ParticipantsDTO dto) {
		try {
			dto.setCreation_date(Calendar.getInstance().getTime());
			Object[] obj = new Object[] { dto.getClient_id(), dto.getFirst_name(), dto.getLast_name(),
					dto.getMiddle_initial(), dto.getAddr1(), dto.getAddr2(),
					dto.getCity(), dto.getState(), dto.getPostal_code(),
					dto.getGender(), dto.getDate_of_birth(), dto.getMember_id(), Constants.ACTIVE, dto.getCreated_by() };

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
			for(ParticipantsDTO dto: participants ) {
				dto.setCreation_date(Calendar.getInstance().getTime());
				Object[] obj = new Object[] { dto.getClient_id(), dto.getFirst_name(), dto.getLast_name(),
						dto.getMiddle_initial(), dto.getAddr1(), dto.getAddr2(),
						dto.getCity(), dto.getState(), dto.getPostal_code(),
						dto.getGender(), dto.getDate_of_birth(), dto.getMember_id(), Constants.ACTIVE, dto.getCreated_by(), dto.getNo_pcp() };	
				objs.add(obj);
			}
			jdbcTemplate.batchUpdate(INSERT_PARTICIPANT, objs);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}
	}
}
