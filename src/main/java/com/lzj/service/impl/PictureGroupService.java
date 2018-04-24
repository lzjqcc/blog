package com.lzj.service.impl;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.FriendDao;
import com.lzj.dao.PictureDao;
import com.lzj.dao.PictureGroupDao;
import com.lzj.dao.dto.FunctionDto;
import com.lzj.dao.dto.PictureGroupDto;
import com.lzj.domain.*;
import com.lzj.service.FunctionService;
import com.lzj.utils.ComentUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查看对方相册需要成为好友，如果不是对方好友不让查看
 * 使用SpringMvc拦截器控制
 */
@Service
public class PictureGroupService implements FunctionService {
    @Autowired
    PictureGroupDao pictureGroupDao;
    @Autowired
    FriendDao friendDao;

    @Autowired
    PictureDao pictureDao;

    public void addPicture(String url, Integer groupid, Integer currentAccountId) {
        Picture picture = new Picture();
        picture.setCurrentAccountId(currentAccountId);
        picture.setPictureSrc(url);
        picture.setPictureGroupId(groupid);
        pictureDao.insertPicture(picture);
    }

    public PictureGroupDto findNotExitsInsert(String groupName, Integer currentAccontId) {
        PictureGroup pictureGroup = pictureGroupDao.findByCurrentAccountIdAndGroupName(currentAccontId, groupName);
        if (pictureGroup == null) {
            pictureGroup = new PictureGroup();
            pictureGroup.setCurrentAccountId(currentAccontId);
            pictureGroup.setPictureGroupName(groupName);
            pictureGroupDao.insertPictureGroup(pictureGroup);
        }
        return buildGroupDto(pictureGroup);
    }

    /**
     * groupId == null 只查询图片组中的一个图片
     * groupId!=null 查询所有图片
     *
     * @param groupId
     * @param page
     * @return
     */
    public ResponseVO<List<PictureGroupDto>> findWithSinglePicture() {
        List<Integer> pictureGroupIds = new ArrayList<>();
        List<PictureGroupDto> list = findCurrentAccountPictureGroup(ComentUtils.getCurrentAccount().getId()).getResult();
        if (CollectionUtils.isEmpty(list)) {
            return ComentUtils.buildResponseVO(true, "操作成功", null);
        }
        list.stream().forEach(t -> pictureGroupIds.add(t.getId()));
        List<String> urls = new ArrayList<>();
        List<Picture> pictures = pictureDao.findGroupByCurrentAccountIdAndGroupIds(ComentUtils.getCurrentAccount().getId(), pictureGroupIds);
        Map<Integer, List<String>> urlMap = pictures.stream().collect(Collectors.toMap(Picture::getPictureGroupId, t -> {
            urls.add(t.getPictureSrc());
            return urls;
        }));
        for (PictureGroupDto dto : list) {
            dto.setPictureURL(urlMap.get(dto.getId()));
        }
        return ComentUtils.buildResponseVO(true, "操作成功", list);
    }

    public ResponseVO<PictureGroupDto> findWithMutiPicture(Integer groupId, Page page) {
        PictureGroup pictureGroup = pictureGroupDao.findByCurrentAccountIdAndGroupId(ComentUtils.getCurrentAccount().getId(), groupId);

        List<Picture> pictures = pictureDao.findByCurrentAccountIdAndGroupId(ComentUtils.getCurrentAccount().getId(), groupId, page);
        if (pictureGroup == null || CollectionUtils.isEmpty(pictures)) {
            return ComentUtils.buildResponseVO(true, "操作成功", null);
        }
        PictureGroupDto dto = buildGroupDto(pictureGroup);
        List<String> urls = new ArrayList<>();
        pictures.stream().forEach(t -> urls.add(t.getPictureSrc()));
        dto.setPictureURL(urls);
        return ComentUtils.buildResponseVO(true, "操作成功", dto);
    }

    /**
     * @param friendId
     * @param page
     * @param
     * @return
     */
    public List<PictureGroupDto> findFriendPictureGroup(Integer friendId, Account currentAccount) {
        if (friendId != null && currentAccount.getId().equals(friendId)) {
            pictureGroupDao.findByCurrentAccountId(friendId);
        }
        return null;

    }

    public ResponseVO<List<PictureGroupDto>> findCurrentAccountPictureGroup(Integer accountId) {
        List<PictureGroup> list = pictureGroupDao.findByCurrentAccountId(accountId);
        List<PictureGroupDto> groupDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return ComentUtils.buildResponseVO(true, "操作成功", groupDtos);
        }
        for (PictureGroup pictureGroup : list) {
            groupDtos.add(buildGroupDto(pictureGroup));
        }
        return ComentUtils.buildResponseVO(true, "操作成功", groupDtos);
    }

    private PictureGroupDto buildGroupDto(PictureGroup pictureGroup) {
        PictureGroupDto groupDto = new PictureGroupDto();
        groupDto.setGroupName(pictureGroup.getPictureGroupName());
        groupDto.setCurrentAccountId(pictureGroup.getCurrentAccountId());
        groupDto.setId(pictureGroup.getId());
        groupDto.setPrictureDesribe(pictureGroup.getPictureDescribe());
        return groupDto;
    }

    @Override
    public boolean hasFunctions(Account currentAccount, FunctionDto dto, Function function) {
        return false;
    }
}
