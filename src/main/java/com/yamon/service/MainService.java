package com.yamon.service;

import java.sql.SQLException;

/**
 * @Author yamon
 * @Date 2023-06-26 15:06
 * @Description
 * @Version 1.0
 */
public interface MainService {
    String getUploadPath() throws SQLException;

    String getWorkPath() throws SQLException;
}
