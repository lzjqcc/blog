package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.dao.GroupDao;
import com.lzj.dao.dto.GroupDto;
import com.lzj.domain.Group;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupDao groupDao;
    public ResponseVO insertGroup(GroupDto dto) {
        ResponseVO responseVO = new ResponseVO();
        if (dto.getCurrentAccountId() == null || StringUtils.isEmpty(dto.getGroupName())) {
            responseVO.setSuccess(false);
            responseVO.setMessage("分组名能为空");
        }

        Group group = new Group();
        buildGroup(group,dto);
        groupDao.insertGroup(group);
        responseVO.setSuccess(true);
        responseVO.setMessage("操作成功");
        return responseVO;
    }
    private void buildGroup(Group group, GroupDto dto) {
        group.setCurrentAccountId(dto.getCurrentAccountId());
        group.setFunctionList(Lists.newArrayList(AuthorityEnum.PICTURE_GROUP_SEE.id, AuthorityEnum.PICTURE_GROUP_COMMENT.id));
        group.setGroupName(dto.getGroupName());
    }

    /**
     * 目前只是根据分组 id 更新groupName
     * @param dto
     */
    public ResponseVO updateGrop(GroupDto dto) {
        ResponseVO responseVO = new ResponseVO();
        if (dto.getId() == null || StringUtils.isEmpty(dto.getGroupName())) {
            responseVO.setSuccess(false);
            responseVO.setMessage("分组名不能为空");
        }
        groupDao.updateGroup(dto);
        responseVO.setSuccess(true);
        responseVO.setMessage("操作成功");
        return responseVO;
    }
    /**
     * 根据currentAccounId 查看分组
     * @param dto
     * @return
     */
    public List<Group> findGroups(GroupDto dto) {
        return groupDao.findGroupsByDto(dto);
    }
    public ResponseVO deleteGroup(GroupDto dto) {
        ResponseVO responseVO = new ResponseVO();
        groupDao.deleteGroup(dto);
        responseVO.setMessage("操作成功");
        responseVO.setSuccess(true);
        return responseVO;
    }
}
