package com.lzj.annotation;

import java.lang.annotation.*;

/**
 * 插入数据时需要插入关联表 比如插入 插入 tb_friend记录 需要插入tb_friend_function
   这种做法缺少要插入的值以及哪个值插入到哪个字段
    1，值的获取
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface EnableRelationTable {
    String relationTableName() ;

    /**
     * 需要插入的字段
     * @return
     */
    String [] value();

    /**
     * 关键列，用来是否判断插入关系表多行
     * @return
     */
    boolean keyRow() default false;
}
