package com.lzj.domain;

import java.io.Serializable;

public class Function extends BaseEntity implements Serializable {
    private String describe;
    private String authority;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
