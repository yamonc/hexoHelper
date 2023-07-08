package com.yamon.service.impl;

import com.yamon.entity.TagEntity;
import com.yamon.service.DaoService;
import com.yamon.service.TagService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

/**
 * @Author yamon
 * @Date 2023-07-07 15:13
 * @Description
 * @Version 1.0
 */
public class TagServiceImpl implements TagService {

    public static final String TABLE_NAME = "tags";

    private DaoService daoService = new DaoServiceImpl();

    @Override
    public List<TagEntity> getAllTags() throws SQLException {
        List<TagEntity> list = new ArrayList<>();
        ResultSet resultSet = daoService.queryResultSet(TABLE_NAME, "1=1");
        if (null != resultSet) {
            while (resultSet.next()) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setTagId(resultSet.getString("id"));
                tagEntity.setTagName(resultSet.getString("name"));
                tagEntity.setCreateTime(resultSet.getString("create_time"));
                list.add(tagEntity);
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
    public boolean saveOneTag(UUID randomUUID, String name) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = dateTimeFormatter.format(LocalDateTime.now());
        try {
            int count = daoService.insert(TABLE_NAME, randomUUID.toString(), name, now);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
