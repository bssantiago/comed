package com.mhc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dao.queries.ClientConstants;
import com.mhc.dto.ClientDTO;
import com.mhc.exceptions.dao.DAOSystemException;

public class ClientsDAOImpl extends BaseDAO<ClientsDAO> implements ClientsDAO {

	private static final Logger LOG = Logger.getLogger(ClientsDAOImpl.class);
	
    @Override
   	public List<ClientDTO> getClients() {
   		List<ClientDTO> clients  = jdbcTemplate.query(ClientConstants.SELECT_CLIENTS_WITH_PROGRAMS,
   				new BeanPropertyRowMapper<ClientDTO>(ClientDTO.class));
   		return clients;
   	}
	
	public ClientDTO getClient(int id) {
		ClientDTO client = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ClientConstants.SELECT_CLIENT_BY_ID, params);
		if (srs.next()) {
			client = new ClientDTO();
			client.setId(srs.getLong("id"));
			client.setHighmark_client_id(srs.getInt("highmark_client_id"));
			client.setVendor(srs.getString("vendor"));
			client.setHighmark_site_code(srs.getInt("highmark_site_code"));
		}
		return client;
	}

	public ClientDTO getClientIdFromPlaform(String platformId) {
		ClientDTO client = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("platform_id", platformId);
		SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(ClientConstants.SELECT_CLIENT_ID_BY_PLATFORM_ID, params);
		if (srs.next()) {
			client = new ClientDTO();
			client.setId(srs.getLong("id"));
		}
		return client;
	} 
	

	@Override
	public void insertBatchCLients(List<ClientDTO> clients) {
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, Object>[] objs = new HashMap[clients.size()];
			int i = 0;
			for (ClientDTO dto : clients) {
				HashMap<String, Object> params = clientToNamedParams(dto);
				objs[i] = params;
				i++;
			}
			String query = "INSERT INTO public.comed_clients " + 
					"(id, name, highmark_client_id, highmark_site_code, contact_name, contact_phone, contact_fax, email_address, addr1, addr2, addr3, city, state, postal_code, phys_last_name, phys_first_name, phys_middle_name, phys_academic_degree, vendor, created_by, creation_date, last_update_by, last_update_date) " + 
					"VALUES "+
					"(:id, :name, :highmark_client_id, :highmark_site_code, :contact_name, :contact_phone, :contact_fax, :email_address, :addr1, :addr2, :addr3, :city, :state, :postal_code, :phys_last_name, :phys_first_name, :phys_middle_name, :phys_academic_degree, :vendor, :created_by, :creation_date, :last_update_by, :last_update_date);";
			namedParameterJdbcTemplate.batchUpdate(query, objs);
		} catch (DAOSystemException dse) {
			LOG.error(dse.getMessage());
			throw dse;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new DAOSystemException(e);
		}
		
	}
	
	private HashMap<String, Object> clientToNamedParams(ClientDTO dto) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", dto.getId());
		params.put("name", dto.getName());
		params.put("highmark_client_id", dto.getHighmark_client_id());
		params.put("highmark_site_code", dto.getHighmark_site_code());
		params.put("contact_name", dto.getContact_name());
		params.put("contact_phone", dto.getContact_phone());
		params.put("contact_fax", dto.getContact_fax());
		params.put("email_address", dto.getEmail_address());
		params.put("addr1", dto.getAddr1());
		params.put("addr2", dto.getAddr2());
		params.put("addr3", dto.getAddr3());
		params.put("city", dto.getCity());
		params.put("state", dto.getState());
		params.put("postal_code", dto.getPostal_code());
		params.put("phys_last_name", dto.getPhys_last_name());
		params.put("phys_first_name", dto.getPhys_first_name());
		params.put("phys_middle_name", dto.getPhys_middle_name());
		params.put("phys_academic_degree", dto.getPhys_academic_degree());
		params.put("vendor", dto.getVendor());
		params.put("created_by", dto.getCreated_by());
		params.put("creation_date", dto.getCreation_date());
		params.put("last_update_by", dto.getLast_update_by());
		params.put("last_update_date", dto.getLast_update_date());
		return params;
	}
}
