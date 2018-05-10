package com.lzj.controller;

import com.lzj.VO.PageVO;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.PictureDto;
import com.lzj.dao.dto.PictureGroupDto;
import com.lzj.domain.Page;
import com.lzj.service.impl.PictureGroupService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pictureGroup")
public class PictureGroupController {
    @Autowired
    private PictureGroupService pictureGroupService;
    @GetMapping("/findGroupWithSinalePicture")
    public ResponseVO<List<PictureGroupDto>> findWithSinglePicture(@RequestParam(value = "friendId", required = false) Integer accountId) {
        if (accountId == null) {
            accountId = ComentUtils.getCurrentAccount().getId();
        }
        return pictureGroupService.findWithSinglePicture(accountId);

    }
    @GetMapping("/findWithMutiPicture")
    public PageVO<List<PictureDto>> findWithMutiPicture(@RequestParam(value = "friendId", required = false) Integer accountId,
                                                        @RequestParam("groupId") Integer groupId,
                                                        @RequestParam("pageSize") Integer size,
                                                        @RequestParam("currentPage")Integer currentPage) {
        Page page = new Page();
        page.setPageSize(size);
        page.setCurrentPage(currentPage);
        if (accountId == null) {
            accountId = ComentUtils.getCurrentAccount().getId();
        }
        return pictureGroupService.findWithMutiPicture(accountId,groupId, page);
    }
}
