package com.lzj.domain;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Date;


public class Account extends BaseEntity implements Serializable {

    //
    private String headIcon;
    private Integer gender;
    private Date birth;
    private String city;
    private Integer age;
    private Integer blogYear;
    private String mobile;
    //职业
    private String occupation;
    //个性签名
    private String personalSignature;
    // 标签
    private String sign;
    private String userName;
    private String password;
    private String email;
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public Account setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public Account setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public Date getBirth() {
        return birth;
    }

    public Account setBirth(Date birth) {
        this.birth = birth;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Account setCity(String city) {
        this.city = city;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Account setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getBlogYear() {
        return blogYear;
    }

    public Account setBlogYear(Integer blogYear) {
        this.blogYear = blogYear;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public Account setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public Account setOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public Account setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public Account setSign(String sign) {
        this.sign = sign;
        return this;
    }
}
