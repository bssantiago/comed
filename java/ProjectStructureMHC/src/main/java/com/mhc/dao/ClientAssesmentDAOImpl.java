package com.mhc.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.GenericSearchDTO;
import com.mhc.exceptions.dao.DAOSystemException;

public class ClientAssesmentDAOImpl extends BaseDAO<ClientAssessmentDTO> implements ClientAssesmentDAO {

	private static final String SELECT_CLIENT_ASSESMENTS = "SELECT * FROM comed_client_assessment as cca WHERE cca.status = true OFFSET ? LIMIT ?;";
	private static final String INSERT_CLIENT_ASSESMENT = "UPDATE comed_client_assessment SET status = false where client_id = ?;"
			+ "INSERT INTO "
			+ "comed_client_assessment( client_id, program_id, calendar_year, program_start_date, program_end_date, program_display_name, extended_screenings, created_by, creation_date, last_updated_by, last_update_date, file_name,status,reward_date)"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	@Override
	public List<ClientAssessmentDTO> getClientsAssesments(GenericSearchDTO search) {
		List<ClientAssessmentDTO> clients = jdbcTemplate.query(
				SELECT_CLIENT_ASSESMENTS, new Object[] { search.getPage(), search.getPageSize() },
				new BeanPropertyRowMapper<ClientAssessmentDTO>(ClientAssessmentDTO.class));
		return clients;
	}

	@Override
	public void setClientAssesment(ClientAssessmentDTO dto) {
		try {
			dto.setCreation_date(Calendar.getInstance().getTime());
			dto.setStatus(true);
			Object[] obj = this.toDataObject(dto);
			jdbcTemplate.update(INSERT_CLIENT_ASSESMENT, obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

	}

	@Override
	protected Object[] toDataObject(ClientAssessmentDTO dto) {
		Object[] obj = new Object[] { dto.getClient_id(), dto.getClient_id(), dto.getProgram_id(),
				dto.getCalendar_year(), dto.getProgram_start_date(), dto.getProgram_end_date(),
				dto.getProgram_display_name(), dto.getExtended_screenings(), dto.getCreated_by(),
				dto.getCreation_date(), dto.getLast_update_by(), dto.getLast_update_date(), dto.getFile_name(),
				dto.isStatus(), dto.getReward_date() };
		return obj;
	}
}
