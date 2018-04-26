package com.mhc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dto.ClientDTO;

public class ClientsDAOImpl extends BaseDAO<ClientsDAO> implements ClientsDAO {

    private static final String SELECT_CLIENTS = 
    		"SELECT * FROM comed_clients cc "
    		+ "inner join comed_client_assessment cca "
    		+ "on cc.id = cca.client_id "
    		+ "where  cca.status = true";
    
    private static final String SELECT_CLIENT_BY_ID = "SELECT *,count(*) OVER() as quantity FROM comed_clients WHERE id = :id";
    
    @Override
	public List<ClientDTO> getClients() {
		List<ClientDTO> clients  = jdbcTemplate.query(SELECT_CLIENTS,
				new BeanPropertyRowMapper<ClientDTO>(ClientDTO.class));
		return clients;
	}
	
	public ClientDTO getClient(int id) {
		ClientDTO client = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(SELECT_CLIENT_BY_ID, params);
		if (srs.next()) {
			client = new ClientDTO();
			client.setId(srs.getLong("quantity"));
			//TODO: set all other parameters
		}
		return client;
	}

	@Override
	protected Object[] toDataObject(ClientsDAO obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
