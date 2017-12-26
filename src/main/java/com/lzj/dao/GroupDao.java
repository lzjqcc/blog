package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.domain.Group;

public interface GroupDao {
    @EnableRelationTable(relationTableName = "tb_group_relation",value = {"function_id","group_id","current_account_id"})
    void insertGroup(Group group);
}
