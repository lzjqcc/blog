package com.lzj.domain;

import java.io.Serializable;

public class Function extends BaseEntity implements Serializable {
    private String describtion;
    private String authority;

    public Function() {
    }

    public Function(Integer id,String describe, String authority) {
        setId(id);
        this.describtion = describe;
        this.authority = authority;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Function{" +
                "describtion='" + describtion + '\'' +
                ", authority='" + authority + '\'' +
                ", id=" + id +
                '}';
    }
}
