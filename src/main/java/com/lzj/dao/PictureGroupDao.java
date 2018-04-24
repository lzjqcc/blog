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

    void insertPictureGroup(PictureGroup group);

    List<PictureGroup> findByCurrentAccountId(@Param("param") Integer currentAccountId);

    PictureGroup findByCurrentAccountIdAndGroupName(@Param("currentId")Integer currentId, @Param("groupName") String groupName);

    PictureGroup findByCurrentAccountIdAndGroupId(@Param("currentAccountId") Integer currentAccountId, @Param("id") Integer id);

}
