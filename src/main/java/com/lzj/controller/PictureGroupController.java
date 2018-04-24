package com.lzj.controller;

import com.lzj.VO.ResponseVO;
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
    @GetMapping("/findCurrentPictureGroup")
    public ResponseVO<List<PictureGroupDto>> findCurrentPictureGroup() {
        return pictureGroupService.findCurrentAccountPictureGroup(ComentUtils.getCurrentAccount().getId());
    }
    @GetMapping("/findGroupWithSinalePicture")
    public ResponseVO<List<PictureGroupDto>> findWithSinglePicture() {
        return pictureGroupService.findWithSinglePicture();
    }
    @GetMapping("/findWithMutiPicture")
    public ResponseVO<PictureGroupDto> findWithMutiPicture(@RequestParam("groupId") Integer groupId,
                                                           @RequestParam("pageSize") Integer size,
                                                           @RequestParam("currentPage")Integer currentPage) {
        Page page = new Page();
        page.setPageSize(size);
        page.setCurrentPage(currentPage);
        return pictureGroupService.findWithMutiPicture(groupId, page);
    }
}
