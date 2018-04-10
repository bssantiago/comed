package com.mhc.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.exceptions.dao.DAOSystemException;

public class ClientAssesmentDAOImpl extends BaseDAO implements ClientAssesmentDAO {

	private static final String SELECT_CLIENT_ASSESMENTS = "SELECT * FROM comed_client_assessment";
	private static final String UPDATE_CLIENT_ASSESMENTS_STATUS = "UPDATE comed_client_assessment SET status = false where client_id = ?";
	private static final String INSERT_CLIENT_ASSESMENT = "INSERT INTO "
			+ "comed_client_assessment( client_id, program_id, calendar_year, program_start_date, program_end_date, program_display_name, extended_screenings, created_by, creation_date, last_updated_by, last_update_date, file_name,status)"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	@Override
	public List<ClientAssessmentDTO> getClientsAssesments() {
		List<ClientAssessmentDTO> clients = jdbcTemplate.query(SELECT_CLIENT_ASSESMENTS,
				new BeanPropertyRowMapper<ClientAssessmentDTO>(ClientAssessmentDTO.class));
		return clients;
	}

	@Override
	public void setClientAssesment(ClientAssessmentDTO dto) {
		try {
			dto.setCreation_date(Calendar.getInstance().getTime());
			dto.setStatus(true);
			Object[] obj = new Object[] { dto.getClient_id(), dto.getProgram_id(), dto.getCalendar_year(),
					dto.getProgram_start_date(), dto.getProgram_end_date(), dto.getProgram_display_name(),
					dto.getExtended_screenings(), dto.getCreated_by(), dto.getCreation_date(), dto.getLast_update_by(),
					dto.getLast_update_date(), dto.getFile_name(), dto.isStatus() };

			jdbcTemplate.update(UPDATE_CLIENT_ASSESMENTS_STATUS, dto.getClient_id());
			jdbcTemplate.update(INSERT_CLIENT_ASSESMENT, obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

	}
}
