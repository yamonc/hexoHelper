package com.yamon.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author yamon
 * @Date 2023-06-26 11:11
 * @Description
 * @Version 1.0
 */
public interface DaoService {

    boolean createTable(String createSQL) throws ClassNotFoundException, SQLException;

    boolean createTable(String ...sql) throws SQLException, ClassNotFoundException;
    /**
     * 新增sql
     * @param params 参数，第一个参数必须是表名
     * @return 是否新增成功
     * @throws SQLException
     */
    int insert(String ...params) throws SQLException;

    void update(String tableName, String setField, String setValue, String conditionField, String conditionValue) throws SQLException;

    List<String> query(String tableName, String condition) throws SQLException;
    ResultSet queryResultSet(String tableName, String condition) throws SQLException;

    int delete(String tableName, String condition) throws SQLException;


}
