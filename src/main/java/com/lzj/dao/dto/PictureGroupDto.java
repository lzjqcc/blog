package com.lzj.dao.dto;

import java.util.List;

public class PictureGroupDto extends GroupDto {
    private static final long serialVersionUID = 3588650417684495331L;
    private String prictureDesribe;
    private List<String> pictureURL;

    public List<String> getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(List<String> pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getPrictureDesribe() {
        return prictureDesribe;
    }

    public void setPrictureDesribe(String prictureDesribe) {
        this.prictureDesribe = prictureDesribe;
    }
}
