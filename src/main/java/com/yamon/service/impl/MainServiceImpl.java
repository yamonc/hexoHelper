package com.yamon.service.impl;

import com.yamon.service.DaoService;
import com.yamon.service.MainService;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yamon
 * @Date 2023-06-26 15:06
 * @Description
 * @Version 1.0
 */
public class MainServiceImpl implements MainService {

    private final DaoService daoService = new DaoServiceImpl();

    @Override
    public String getUploadPath() throws SQLException {
        List<String> properties = daoService.query("properties", "item = 'uploadPath'");
        if (properties.isEmpty()){
            return null;
        }
        return properties.get(0);
    }

    @Override
    public String getWorkPath() throws SQLException {
        List<String> properties = daoService.query("properties", "item = 'workPath'");
        if (properties.isEmpty()){
            return null;
        }
        return properties.get(0);
    }
}
