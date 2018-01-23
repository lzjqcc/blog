package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.constant.TYPEEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Conference extends BaseEntity implements Serializable {
    private Date beginTime;
    private Date endTime;
    private Boolean email;
    // 会议发起人 tb_account
    private Integer sponsorId;
    private String details;
    private List<Integer> memberIds;
    //会议主题
    private String theme;
    private String place;
    private List<Integer> functionList ;

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    /**
     * 存数据库类型 [1,2]
     * @return
     */
    @EnableRelationTable(relationTableName = "tb_conference_function",value = "function_id",type = TYPEEnum.STRING)
    public List<Integer> getFunctionList() {
        return functionList;
    }
    public void setFunctionList(List<Integer> functionList) {
        this.functionList = functionList;
    }
    @EnableRelationTable(relationTableName = "tb_conference_function",value = "member_id",keyRow = true)
    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    @Override
    @EnableRelationTable(relationTableName = "tb_conference_function",value = "conference_id")
    public Integer getId() {
        return super.getId();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    @EnableRelationTable(relationTableName = "tb_conference_function",value = "sponsor_id")
    public Integer getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
