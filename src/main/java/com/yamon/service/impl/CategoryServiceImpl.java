package com.yamon.service.impl;

import com.yamon.entity.CategoryEntity;
import com.yamon.entity.TagEntity;
import com.yamon.service.CategoryService;
import com.yamon.service.DaoService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yamon
 * @Date 2023-07-08 10:13
 * @Description
 * @Version 1.0
 */
public class CategoryServiceImpl implements CategoryService {

    public static final String TABLE_NAME = "categories";

    private DaoService daoService = new DaoServiceImpl();
    @Override
    public List<CategoryEntity> getAllCateogies() throws SQLException {
        List<CategoryEntity> list = new ArrayList<>();
        ResultSet resultSet = daoService.queryResultSet(TABLE_NAME, "1=1");
        if (null != resultSet) {
            while (resultSet.next()) {
                CategoryEntity  entity = new CategoryEntity();
                entity.setCategoryId(resultSet.getString("id"));
                entity.setCategoryName(resultSet.getString("name"));
                entity.setCategoryCreateTime(resultSet.getString("create_time"));
                list.add(entity);
            }
        }
        return list;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            int row = daoService.delete(TABLE_NAME, "id = " + "'" + id + "'");
            // 删除成功
            return row > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveOneCategory(String id, String name) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = dateTimeFormatter.format(LocalDateTime.now());
        try {
            int count = daoService.insert(TABLE_NAME, id, name, now);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
