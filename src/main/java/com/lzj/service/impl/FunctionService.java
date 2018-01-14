package com.lzj.service.impl;

import com.lzj.constant.AuthorityEnum;
import com.lzj.dao.FunctionDao;
import com.lzj.domain.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionService {
    @Autowired
    private FunctionDao functionDao;
    @PostConstruct
    public void inject() {
        List<Function> functions = functionDao.findAll();
        if (functions.size() != 0) {
            return;
        }
        List<Function> list = new ArrayList<>();
        list.add(new Function(AuthorityEnum.GROUP_PICTURE_GROUP_SEE.id, AuthorityEnum.GROUP_PICTURE_GROUP_SEE.describe, AuthorityEnum.GROUP_PICTURE_GROUP_SEE.authority));
        list.add(new Function(AuthorityEnum.GROUP_PICTURE_GROUP_COMMENT.id, AuthorityEnum.GROUP_PICTURE_GROUP_COMMENT.describe, AuthorityEnum.GROUP_PICTURE_GROUP_COMMENT.authority));
        list.add(new Function(AuthorityEnum.FRIEND_PICTURE_GROUP_SEE.id, AuthorityEnum.FRIEND_PICTURE_GROUP_SEE.describe, AuthorityEnum.FRIEND_PICTURE_GROUP_SEE.authority));
        list.add(new Function(AuthorityEnum.FRIEND_PICTURE_GROUP_COMMENT.id, AuthorityEnum.FRIEND_PICTURE_GROUP_COMMENT.describe, AuthorityEnum.FRIEND_PICTURE_GROUP_COMMENT.authority));
       functionDao.insertFunctions(list);
    }
}
