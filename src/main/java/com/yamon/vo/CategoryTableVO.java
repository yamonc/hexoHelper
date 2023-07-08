package com.yamon.vo;

import com.yamon.entity.CategoryEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @Author yamon
 * @Date 2023-07-08 10:07
 * @Description
 * @Version 1.0
 */
public class CategoryTableVO extends CategoryEntity {
    public StringProperty idProperty() {
        return new SimpleStringProperty(super.getCategoryId());
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(super.getCategoryName());
    }

    public StringProperty createTimeProperty() {
        return new SimpleStringProperty(super.getCategoryCreateTime());
    }
}
