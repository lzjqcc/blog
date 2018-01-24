package com.lzj.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 默认这次会议成员都有查看权限
 */
public class ConferenceFunction extends BaseEntity implements Serializable {
    private List<Integer> functionIds;
    private Integer conferenceId;
    private Integer sponsorId;
    private Integer memberId;

    public List<Integer> getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(List<Integer> functionIds) {
        this.functionIds = functionIds;
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

    @Override
    public String toString() {
        return "ConferenceFunction{" +
                "functionIds=" + functionIds +
                ", conferenceId=" + conferenceId +
                ", sponsorId=" + sponsorId +
                ", memberId=" + memberId +
                ", id=" + id +
                '}';
    }
}
