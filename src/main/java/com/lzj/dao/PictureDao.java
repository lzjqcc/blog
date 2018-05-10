package com.lzj.dao;

import com.lzj.domain.Page;
import com.lzj.domain.Picture;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import java.util.List;
@Repository
public interface PictureDao {
    void insertPicture(Picture picture);
    List<Picture> findGroupByCurrentAccountIdAndGroupIds(@Param("currentAccountId")Integer currentAccountId, @Param("list") List<Integer> groupIds);

    List<Picture> findByCurrentAccountIdAndGroupId(@Param("currentAccountId") Integer currentAccountId, @Param("groupId") Integer groupId, Page page);

}
