package com.holybell.homework08.model;

import java.util.Date;

public class User {

    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private Date createTime;
    private Date updateTime;
    private Integer status;

    public User() {

    }

    public User(String username, String sex, Integer age) {
        this.id = System.currentTimeMillis();
        this.name = username;
        this.sex = sex;
        Date now = new Date();
        this.createTime = now;
        this.updateTime = now;
        this.status = 1;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
