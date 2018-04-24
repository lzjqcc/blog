package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.GroupDto;
import com.lzj.domain.Account;
import com.lzj.domain.Group;
import com.lzj.service.impl.GroupService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    /**
     * 添加分组
     * @param dto
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/addGroup")
    public ResponseVO addGroup(@RequestBody GroupDto dto) {
        Account account = ComentUtils.getCurrentAccount();
        dto.setCurrentAccountId(account.getId());
        return groupService.insertGroup(dto);
    }

    /**
     * 更新分组
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/updateGroup")
    public ResponseVO operatorGroup(@RequestBody GroupDto dto) {
        return groupService.updateGrop(dto);
    }

    /**
     * 获取好友分组
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "findGroups")
    public ResponseVO<List<Group>> findGroups() {
        Account account = ComentUtils.getCurrentAccount();
        GroupDto dto = new GroupDto();
        dto.setCurrentAccountId(account.getId());
        return groupService.findGroups(dto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "deleteGroup")
    public ResponseVO deleteGroup(@RequestBody GroupDto dto) {
        return groupService.deleteGroup(dto);
    }
}
