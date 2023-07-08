package com.yamon.service;

import com.yamon.entity.TagEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * @Author yamon
 * @Date 2023-07-07 15:12
 * @Description
 * @Version 1.0
 */
public interface TagService {

    List<TagEntity> getAllTags() throws SQLException;

    boolean deleteById(String id);

    boolean saveOneTag(UUID randomUUID, String name);
}
