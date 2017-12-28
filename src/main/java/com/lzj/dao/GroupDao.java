package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.dao.dto.GroupDto;
import com.lzj.domain.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDao {
    @EnableRelationTable(relationTableName = "tb_group_relation",value = {"function_id","group_id","current_account_id"})
    void insertGroup(Group group);

    /**
     * 根据id 更新groupName
     * @param dto
     */
    void updateGroup(GroupDto dto);

    /**
     * currentAccountId
     * @param dto
     * @return
     */
    List<Group> findGroupsByDto(GroupDto dto);

    /**
     * id
     * @param dto
     */
    void deleteGroup(GroupDto dto);

}
