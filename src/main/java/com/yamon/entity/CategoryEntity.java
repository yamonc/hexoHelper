package com.yamon.entity;

/**
 * @Author yamon
 * @Date 2023-07-08 10:07
 * @Description
 * @Version 1.0
 */
public class CategoryEntity {
    private String categoryId;
    private String categoryName;
    private String categoryCreateTime;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCreateTime() {
        return categoryCreateTime;
    }

    public void setCategoryCreateTime(String categoryCreateTime) {
        this.categoryCreateTime = categoryCreateTime;
    }
}
