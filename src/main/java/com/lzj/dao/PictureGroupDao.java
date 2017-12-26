package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.domain.Function;
import com.lzj.domain.Page;
import com.lzj.domain.PictureGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 相册权限：查看，评论
 */
@Repository
public interface PictureGroupDao {
    @EnableRelationTable(relationTableName = "tb_picture_group_function",value = {"picture_group_id","function_id","current_account_id"})
    void insertPictureGroupDao(@Param("param") PictureGroup group);

    List<PictureGroup> findByCurrentAccountId(@Param("param") Integer currentAccountId, Page page);

    /**
     * 查询对这个照片组要执行的操作需要的权限
     * @param accountId
     * @param pictureGroupId
     * @return
     */
    List<Function> findPictureGroupFunction(Integer ownerId,Integer pictureGroupId);
}
