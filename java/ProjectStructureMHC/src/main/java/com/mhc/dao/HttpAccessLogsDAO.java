package com.mhc.dao;

import com.mhc.dto.HttpAccessLogsDTO;

/**
 * Created by new on 13.03.2018.
 */
public interface HttpAccessLogsDAO {
    void saveLogs(HttpAccessLogsDTO dto);
}
