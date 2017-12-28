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

    void updateGroup(GroupDto dto);
    List<Group> findGroupByDto(GroupDto dto);

}
