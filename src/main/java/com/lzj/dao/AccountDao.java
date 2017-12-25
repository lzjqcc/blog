package com.lzj.dao;

import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.domain.Function;
import com.lzj.domain.Page;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by li on 17-8-6.
 */
@Repository("userDao")
public interface AccountDao {
    void saveAccount(Account accountDto);

    void updateUser(@Param("param") AccountDto dto);

    /**
     * name==null根据email查询
     * email==null 根据name查询
     * email=null&&name=null 查询所有
     *
     * @param dto
     * @return
     */
    Account findByDto(@Param("param") AccountDto dto);

    List<Function> findFriendFunction(Integer accountId, Integer friendId);

    List<Account> findAll(Page page);
}
