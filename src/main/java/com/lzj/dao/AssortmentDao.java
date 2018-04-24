package com.lzj.dao;

import com.lzj.domain.Assortment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("assortmentDao")
public interface AssortmentDao {
    void insertAssortment(Assortment assortment);
    Assortment findByUserIdAndName(@Param("userId") Integer userId,@Param("name") String name);

    List<Assortment> findByIds(@Param("ids") List<Integer> assortmentIds);
    /**
     * name 或num可以微null,id不能微null
     * @param name
     * @param num
     * @param id
     */
    void updateAssortment(@Param("name") String name,@Param("num") Integer num,@Param("id") Integer id);

    List<Assortment> findAssortmentByUserId(Integer userId);
}
