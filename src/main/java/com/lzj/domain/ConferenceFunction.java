package com.lzj.domain;

import java.io.Serializable;

/**
 * 默认这次会议成员都有查看权限
 */
public class ConferenceFunction extends BaseEntity implements Serializable {
    private Integer functionId;
    private Integer conferenceId;
    private Integer sponsorId;
    private Integer memberId;

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Integer getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
