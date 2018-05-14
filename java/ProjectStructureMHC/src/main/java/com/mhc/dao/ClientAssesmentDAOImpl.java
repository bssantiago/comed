package com.mhc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dao.queries.ClientAssessmentConstants;
import com.mhc.dto.ClientAssessmentBaseDTO;
import com.mhc.dto.ClientAssessmentDTO;
import com.mhc.dto.GenericSearchDTO;
import com.mhc.dto.SearchResultDTO;
import com.mhc.exceptions.dao.DAOSystemException;

public class ClientAssesmentDAOImpl extends BaseDAO<ClientAssessmentDTO> implements ClientAssesmentDAO {

	private static final Logger LOG = Logger.getLogger(ClientAssesmentDAOImpl.class);
	
	@Override
	public SearchResultDTO<ClientAssessmentDTO> getClientsAssesments(GenericSearchDTO search) {
		SearchResultDTO<ClientAssessmentDTO> result = new SearchResultDTO<ClientAssessmentDTO>();
		int offset = (search.getPage() - 1) * search.getPageSize();
		List<ClientAssessmentDTO> clients = jdbcTemplate.query(ClientAssessmentConstants.SELECT_CLIENT_ASSESMENTS,
				new Object[] { offset, search.getPageSize() },
				new BeanPropertyRowMapper<ClientAssessmentDTO>(ClientAssessmentDTO.class));

		int pages = clients.get(0).getFull_count() / search.getPageSize() + 1;
		result.setPages(pages);
		result.setItems(clients);
		return result;
	}

	@Override
	public boolean markAsDownload(ClientAssessmentBaseDTO dto) {
		try {
			Object[] obj = new Object[] { dto.getClient_id(), dto.getProgram_id() };
			if (dto.isMarked()) {
				jdbcTemplate.update(ClientAssessmentConstants.UPDATE_CLIENT_ASSESMENTS, obj);
				return true;
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("client_id", dto.getClient_id());
				params.put("program_id", dto.getProgram_id());
				SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ClientAssessmentConstants.SELECT_CLIENT_ASSESSMENTS_MARKED, params);
				Integer count = 0;
				while (srs.next()) {
					count = srs.getInt("count");
				}

				return count > 0;
			}

		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}

	}

}
