package com.mhc.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.mhc.dto.ClientDTO;

public class ClientsDAOImpl extends BaseDAO implements ClientsDAO {

    private static final String SELECT_CLIENTS = "SELECT * FROM comed_clients";

	@Override
	public List<ClientDTO> getClients() {
		List<ClientDTO> clients  = jdbcTemplate.query(SELECT_CLIENTS,
				new BeanPropertyRowMapper<ClientDTO>(ClientDTO.class));
		return clients;
	}
}
