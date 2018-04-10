package com.mhc.dao;

import com.mhc.dto.HttpAccessLogsDTO;
import com.mhc.exceptions.dao.DAOSystemException;

/**
 * Created by new on 13.03.2018.
 */
public class HttpAccessLogsDAOImpl extends BaseDAO<HttpAccessLogsDTO> implements HttpAccessLogsDAO {

	private static final String INSERT_HTTP_ACCESS_LOGS = "insert into http_access_logs(requested_by,source_ip,document_id,nonce,path,method) values (?,?,?,?,?,?)";

	@Override
	public void saveLogs(HttpAccessLogsDTO dto) {
		try {
			Object[] obj = this.toDataObject(dto);
			jdbcTemplate.update(INSERT_HTTP_ACCESS_LOGS, obj);
		} catch (DAOSystemException dse) {
			throw dse;
		} catch (Exception e) {
			throw new DAOSystemException(e);
		}

	}

	@Override
	protected Object[] toDataObject(HttpAccessLogsDTO dto) {
		Object[] obj = new Object[] { dto.getRequestedBy(), dto.getSourceIP(), dto.getDocumentId(), dto.getNonce(),
				dto.getPath(), dto.getMethod() };
		return obj;
	}
}
