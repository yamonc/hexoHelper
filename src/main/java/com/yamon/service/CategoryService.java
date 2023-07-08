package com.yamon.service;

import com.yamon.entity.CategoryEntity;
import com.yamon.entity.TagEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yamon
 * @Date 2023-07-08 10:13
 * @Description
 * @Version 1.0
 */
public interface CategoryService {

    List<CategoryEntity> getAllCateogies() throws SQLException;

    boolean deleteById(String id);

    boolean saveOneCategory(String id, String name);
}
