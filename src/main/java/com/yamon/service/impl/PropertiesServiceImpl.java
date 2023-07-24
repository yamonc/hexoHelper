package com.yamon.service.impl;

import com.yamon.service.DaoService;
import com.yamon.service.PropertiesService;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author yamon
 * @Date 2023-07-24 15:04
 * @Description
 * @Version 1.0
 */
public class PropertiesServiceImpl implements PropertiesService {

    public static final String TABLE_NAME = "properties";

    private DaoService daoService = new DaoServiceImpl();

    @Override
    public void initData() {
        try {
            daoService.insert(TABLE_NAME, "abstract", "有东西被加密了, 请输入密码查看.");
            daoService.insert(TABLE_NAME, "message", "您好, 这里需要密码.");
            daoService.insert(TABLE_NAME, "wrong_pass_message", "抱歉, 这个密码看着不太对, 请再试试.");
            daoService.insert(TABLE_NAME, "wrong_hash_message", "抱歉, 这个文章不能被校验, 不过您还是能看看解密后的内容.");
            daoService.insert(TABLE_NAME, "password", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getValueByKey(String key) throws SQLException {
        String content = "";
        ResultSet resultSet = daoService.queryResultSet(TABLE_NAME, "item = " + "'" + key + "'");
        while (resultSet.next()) {
            content = resultSet.getString("content");
        }
        return content;
    }

    @Override
    public void updateValueByKey(String message, String updateValue) throws SQLException {
        daoService.update(TABLE_NAME, "content", updateValue, "item", message);
    }

    @Override
    public boolean isKeyExist(String key) throws SQLException {
        ResultSet resultSet = daoService.queryResultSet(TABLE_NAME, "item = " + "'" + key + "'");
        return resultSet.next();
    }
}
