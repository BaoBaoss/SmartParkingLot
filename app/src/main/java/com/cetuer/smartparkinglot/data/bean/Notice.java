package com.cetuer.smartparkinglot.data.bean;

/**
* 公告实体
* 
* @author zhangqb
* @date 2022/4/13 10:28
*/
public class Notice {
    private Integer id;

    private String title;

    private String content;

    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}