package com.lzj.annotation;

import com.lzj.constant.TYPEEnum;

import javax.sound.sampled.FloatControl;
import java.lang.annotation.*;

/**
 * 插入数据时需要插入关联表 比如插入 插入 tb_friend记录 需要插入tb_friend_function
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
    String [] value() default "";

    /**
     * 关键列，用来是否判断插入关系表多行
     * @return
     */
    boolean keyRow() default false;

    /**
     * 放入Mysql 的类型
     * @return
     */
    TYPEEnum type() default TYPEEnum.INTEGER;
}
