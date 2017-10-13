package com.lzj.VO;

import com.lzj.domain.BaseEntity;

import java.util.List;

public class ArticleMongo extends BaseEntity {
    private String content;
    private List<String> picList;

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleMongo{" +
                "id="+getId()+
                "createTime="+getCreateTime()+
                "updateTime="+getUpdateTime()+
                "content='" + content + '\'' +
                '}';
    }
}
