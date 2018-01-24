package com.lzj.dao.dto;

import com.lzj.domain.ConferenceFlow;

import java.util.Date;
import java.util.List;

public class ConferenceDto {
    private Integer conferenceId;
    private Date beginTime;
    private Date endTime;
    private Boolean email;
    // 会议发起人 tb_account
    private Integer sponsorId;
    private String details;
    //会议主题
    private String theme;
    private String place;

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }
}
