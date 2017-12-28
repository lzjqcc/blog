package com.lzj.service.impl;

import com.lzj.dao.GroupDao;
import com.lzj.dao.dto.GroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupDao groupDao;
    public void insertGroup(GroupDto dto) {

    }

}
