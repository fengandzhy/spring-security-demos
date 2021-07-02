package org.zhouhy.jwt.domain;

import java.util.Date;

public class Payload <T>{
    private String Id;
    private T userInfo;
    private Date expiration;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public T getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(T userInfo) {
        this.userInfo = userInfo;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
