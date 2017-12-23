package com.lzj.utils;

import com.lzj.domain.BaseEntity;
import com.lzj.exception.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtils {
    /**
     * 找出entity 中值为null属性的名字
     *
     * @param entity
     * @return
     */
    public static String[] findNullFieldName(Object entity) {
        Field[] allFileds = entity.getClass().getFields();
        List<String> nullFiledNames = new ArrayList<>();
        try {
            for (Field field : allFileds) {
                field.setAccessible(true);
                Object object = field.get(entity);
                if (object == null) {
                    nullFiledNames.add(field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            return null;
        }
        String[] array = new String[nullFiledNames.size()];
        for (int i = 0; i < nullFiledNames.size(); i++) {
            array[i] = nullFiledNames.get(i);
        }
        return array;
    }

    public static void copyFieldValue(Object source, Object targe, String... copyName) {
        if (source == null || targe == null) {
            throw new SystemException(300, "source || targe 为null");
        }

        Class sourceClass = source.getClass();
        Class targeClass = targe.getClass();
        try {
            for (String name : copyName) {
                Field sourceFiled = ReflectionUtils.findField(sourceClass, name);
                Field targeField = ReflectionUtils.findField(targeClass,name);
                if (sourceFiled == null || targeField== null) {
                   throw new SystemException(300,"字段"+name+"在"+source.getClass()+":"+targe.getClass()+"中找不到");
                }
                sourceFiled.setAccessible(true);
                targeField.setAccessible(true);
                targeField.set(targe,sourceFiled.get(source));
            }
        } catch (IllegalAccessException e) {
            StringBuilder builder = new StringBuilder();
            for (String name : copyName) {
                builder.append(name);
            }
            String args = source.getClass().getSimpleName()+":"+targe.getClass().getSimpleName()+":"+builder.toString();
            throw new SystemException(300, "没有字段访问权限", ReflectUtils.class.toString(), "copyFieldValue", args,e.toString());
        }
    }

}
