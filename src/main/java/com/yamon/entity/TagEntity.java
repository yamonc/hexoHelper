package com.yamon.entity;

/**
 * @Author yamon
 * @Date 2023-07-07 15:08
 * @Description
 * @Version 1.0
 */
public class TagEntity {
    private String tagName;
    private String tagId;
    private String createTime;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
