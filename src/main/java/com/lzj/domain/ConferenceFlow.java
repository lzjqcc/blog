package com.lzj.domain;

import java.io.Serializable;

public class ConferenceFlow extends BaseEntity implements Serializable{
    //会议id
    private Integer conferenceId;
    private String describe;
    private String record;
    //会议记录人 tb_account
    private String recoderId;

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getRecoderId() {
        return recoderId;
    }

    public void setRecoderId(String recoderId) {
        this.recoderId = recoderId;
    }

}
