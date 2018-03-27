package com.mhc.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Created by new on 13.03.2018.
 */
public interface InitDAO {
    SqlRowSet getSecurityKeys();
}
