package com.yamon.convert.dto;

/**
 * @Author yamon
 * @Date 2023-06-25 16:04
 * @Description
 * @Version 1.0
 */
public class PropertiesEntity {
    String item;
    String content;

    public PropertiesEntity(String item, String content) {
        this.item = item;
        this.content = content;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

