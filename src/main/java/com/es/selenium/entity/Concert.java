package com.es.selenium.entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @program: es-demo
 * @description:
 * @author: 陈家乐
 * @create: 2019-04-12 09:54
 **/

public class Concert {
    private Integer position;//票价位置
    private Integer count;//票的数量
    private Integer buyCount;//购买人身份个数

    private String loginUrl;//登录页

    private String buyUrl;//购买页

    public Concert(Integer position,Integer count,Integer buyCount){
        this.position=position;
        this.count=count;
        this.buyCount=buyCount;
    }

    public Concert() {
    }


    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }



}
