package com.lzj.VO;

import com.lzj.domain.Page;

import java.io.Serializable;

public class PageVO<T> implements Serializable {
    private Page page;
    private T result;
    private Boolean success;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
