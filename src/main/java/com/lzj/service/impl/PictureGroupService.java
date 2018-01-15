package com.lzj.service.impl;

import com.lzj.dao.FriendDao;
import com.lzj.dao.PictureGroupDao;
import com.lzj.dao.dto.FunctionDto;
import com.lzj.domain.*;
import com.lzj.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查看对方相册需要成为好友，如果不是对方好友不让查看
 * 使用SpringMvc拦截器控制
 */
@Service
public class PictureGroupService implements FunctionService{
    @Autowired
    PictureGroupDao pictureGroupDao;
    @Autowired
    FriendDao friendDao;
    /**
     *
     * @param friendId
     * @param page
     * @param
     * @return
     */
    public List<PictureGroup> findFriendPictureGroup(Integer friendId, Page page, Account currentAccount) {
        if (friendId != null && currentAccount.getId().equals(friendId)) {
            return pictureGroupDao.findByCurrentAccountId(friendId, page);
        }
        return null;

    }


    @Override
    public boolean hasFunctions(Account currentAccount, FunctionDto dto, Function function) {
        return false;
    }
}
