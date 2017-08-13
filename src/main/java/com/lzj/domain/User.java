package com.lzj.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by li on 17-8-6.
 */
@Entity
@Table(name = "user")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "@id")
public class User extends BaseEntity{

    private String userName;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private String password;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private String email;
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Enumerated(value =EnumType.STRING)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    public static enum Role{
        ROLE_ADMIN,ROLE_USER
    }
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Override
    public Date getCreateTime() {
        return super.getCreateTime();
    }
}
