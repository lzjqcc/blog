package com.lzj.VO;

import java.io.Serializable;

public class ResponseVO implements Serializable{

    private static final long serialVersionUID = -4423787818617420423L;
    private Boolean success;
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
