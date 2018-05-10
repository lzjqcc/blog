package com.lzj.dao.dto;

import java.util.List;

public class PictureGroupDto extends GroupDto {
    private static final long serialVersionUID = 3588650417684495331L;
    private String prictureDesribe;
    private List<String> pictureURLs;
    private Integer pictureCount;

    public Integer getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(Integer pictureCount) {
        this.pictureCount = pictureCount;
    }

    public List<String> getPictureURLs() {
        return pictureURLs;
    }

    public void setPictureURLs(List<String> pictureURLs) {
        this.pictureURLs = pictureURLs;
    }

    public String getPrictureDesribe() {
        return prictureDesribe;
    }

    public void setPrictureDesribe(String prictureDesribe) {
        this.prictureDesribe = prictureDesribe;
    }
}
