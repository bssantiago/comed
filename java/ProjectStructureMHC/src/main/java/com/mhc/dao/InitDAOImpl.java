package com.mhc.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Created by new on 13.03.2018.
 */
public class InitDAOImpl extends BaseDAO<Object> implements InitDAO {

    private static final String GET_SECURITY_KEYS = "select salt, doc_key, log_key from app_keys limit 1";

    @Override
    public SqlRowSet getSecurityKeys() {
        return jdbcTemplate.queryForRowSet(GET_SECURITY_KEYS);
    }

}
