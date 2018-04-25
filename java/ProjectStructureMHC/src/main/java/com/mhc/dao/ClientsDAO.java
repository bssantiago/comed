package com.mhc.dao;

import java.util.List;

import com.mhc.dto.ClientDTO;

/**
 * Created by new on 13.03.2018.
 */
public interface ClientsDAO {
	List<ClientDTO> getClients();
	ClientDTO getClient(int id);
}
