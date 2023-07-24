package com.yamon.convert.dto;

/**
 * @Author yamon
 * @Date 2023-06-14 14:00
 * @Description
 * @Version 1.0
 */
public class FrontMatterEntity {
    /**
     * 【必需】页面标题
     */
    private String title;

    /**
     * 【必需】页面创建日期
     */
    private String date;
    /**
     * 【必需】友情链接和说说界面三个页面需要配置
     */
    private String type;
    /**
     * 【可选】显示页面评论模块(默认 true)
     */
    private String comment;
    /**
     * 【可选】文章描述
     */
    private String description;
    /**
     * 【可选】文章标签
     */
    private String tags;
    /**
     * 【可选】文章分类
     */
    private String categories;
    /**
     * 【可选】文章关键字
     */
    private String keywords;
    /**
     * 【可选】文章推荐相关，设置后文章会在hometop中显示
     */
    private String recommend;
    /**
     * 【可选】文章缩略图
     */
    private String cover;

    /**
     * 【可选】显示侧边栏 (默认 true)
     */
    private String aside;
    /**
     * 【可选】作者地址 (默认 北京)
     */
    private String locate;
    /**
     * 【可选】显示文章版权模块 (默认 原创)
     */
    private String cc;
    /**
     * 【可选】页面权重SEO用（默认 100）
     */
    private String excerpt;

    /**
     * 以下都是密码的字段
     */
    private String passwordAbstract;
    private String passwordMessage;
    private String wrongPassMessage;
    private String wrongHashMessage;
    private String password;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAside() {
        return aside;
    }

    public void setAside(String aside) {
        this.aside = aside;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getPasswordAbstract() {
        return passwordAbstract;
    }

    public void setPasswordAbstract(String passwordAbstract) {
        this.passwordAbstract = passwordAbstract;
    }

    public String getPasswordMessage() {
        return passwordMessage;
    }

    public void setPasswordMessage(String passwordMessage) {
        this.passwordMessage = passwordMessage;
    }

    public String getWrongPassMessage() {
        return wrongPassMessage;
    }

    public void setWrongPassMessage(String wrongPassMessage) {
        this.wrongPassMessage = wrongPassMessage;
    }

    public String getWrongHashMessage() {
        return wrongHashMessage;
    }

    public void setWrongHashMessage(String wrongHashMessage) {
        this.wrongHashMessage = wrongHashMessage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
