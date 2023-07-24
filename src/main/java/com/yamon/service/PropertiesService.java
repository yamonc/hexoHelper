package com.yamon.service;

import java.sql.SQLException;

/**
 * @Author yamon
 * @Date 2023-07-24 15:04
 * @Description
 * @Version 1.0
 */
public interface PropertiesService {

    void initData();

    boolean isKeyExist(String key) throws SQLException;

    String getValueByKey(String key) throws SQLException;

    void updateValueByKey(String message, String updateValue) throws SQLException;
}
