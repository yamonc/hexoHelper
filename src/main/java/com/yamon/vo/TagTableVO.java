package com.yamon.vo;

import com.yamon.entity.TagEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 * @Author yamon
 * @Date 2023-07-07 15:47
 * @Description
 * @Version 1.0
 */
public class TagTableVO extends TagEntity {
    private Button buttonOne;

    private Button buttonTwo;

    public TagTableVO() {
        this.buttonOne = new Button("修改");
        this.buttonTwo = new Button("删除");
    }

    public Button getButtonOne() {
        return buttonOne;
    }

    public void setButtonOne(Button buttonOne) {
        this.buttonOne = buttonOne;
    }

    public Button getButtonTwo() {
        return buttonTwo;
    }

    public void setButtonTwo(Button buttonTwo) {
        this.buttonTwo = buttonTwo;
    }

    public StringProperty idProperty() {
        return new SimpleStringProperty(super.getTagId());
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(super.getTagName());
    }

    public StringProperty createTimeProperty() {
        return new SimpleStringProperty(super.getCreateTime());
    }

}
