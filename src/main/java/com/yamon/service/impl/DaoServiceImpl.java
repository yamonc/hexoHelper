package com.yamon.service.impl;

import com.yamon.service.DaoService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author yamon
 * @Date 2023-06-26 11:12
 * @Description
 * @Version 1.0
 */
public class DaoServiceImpl implements DaoService {
    public static final String USER_DIRECTORY = Objects.requireNonNull(DaoServiceImpl.class.getResource("/db")).getPath();
    private static final String CLASS_NAME = "org.sqlite.JDBC";
//    private static final String DB_URL = "jdbc:sqlite:" + "D:\\sugon\\code_java\\code\\convert\\src\\main\\resources\\db\\hexoHelper.db";

//  打包环境
//    private static final String DB_URL = "jdbc:sqlite:db/hexoHelper.db";

    // dev环境
    private static final String DB_URL = "jdbc:sqlite:" + USER_DIRECTORY + "hexoHelper.db";

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createTable(String createSQL) throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        return stmt.execute(createSQL);
    }

    @Override
    public boolean createTable(String... sql) throws SQLException, ClassNotFoundException {
        Class.forName(CLASS_NAME);
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        for (String s : sql) {
            stmt.addBatch(s);
        }
        int[] executeBatch = stmt.executeBatch();
        return executeBatch.length == sql.length;
    }

    @Override
    public int insert(String... params) throws SQLException {
        if (conn != null) {
            String tableName = params[0];
            int count = params.length - 1;
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(tableName).append(" VALUES(");
            sql.append("?,".repeat(count));
            // 去掉最后一个逗号
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            for (int i = 1; i < params.length; i++) {
                pstmt.setString(i, params[i]);
            }
            return pstmt.executeUpdate();
        }
        return 0;
    }

    @Override
    public void update(String tableName, String setField, String setValue, String conditionField, String conditionValue) throws SQLException {
        if (conn != null) {
            String sql = "UPDATE " + tableName + " SET " + setField + " = " + "'" + setValue + "'" +
                    " WHERE " + conditionField + "=" + "'" + conditionValue + "'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public List<String> query(String tableName, String condition) throws SQLException {
        List<String> result = new ArrayList<>();
        if (conn != null) {
            String sql = "SELECT * FROM " + tableName + " WHERE " + condition;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString(2));
            }
        } else {
            System.out.println("conn is null");
        }
        return result;
    }
}
