package com.lzj.aop;

import com.lzj.domain.BaseEntity;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class CommentDataAop {
    Log log = LogFactory.getLog(CommentDataAop.class);
    /**
     * 保存数据自动插入时间
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before(value = "execution(* com.lzj.dao.*.insert*(*))")
    public void inserAop(JoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getArgs()[0];
        Class clazz = object.getClass();
        if (hasTimeField(clazz)) {
            injectTime(object,false);
        }

    }

    /**
     * 更新数据自动更新时间
     */
    @Before(value = "execution(* com.lzj.dao.*.update*(*))")
    public void updateAop(JoinPoint joinPoint) throws Throwable {
        if (hasTimeField(joinPoint.getArgs()[0].getClass())) {
            injectTime(joinPoint.getArgs()[0],true);
        }
    }

    private void injectTime(Object object, boolean isUpdate) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Date date = new Date();
        Method updateMethod=object.getClass().getMethod("setUpdateTime",Date.class);
        updateMethod.invoke(object,date);
        if (!isUpdate){
            Method createMethod=object.getClass().getMethod("setCreateTime",Date.class);
            createMethod.invoke(object,date);
        }

    }

    /**
     * todo 以后根据方法来判断
     * 判断是否有时间字段
     *
     * @param clazz
     * @return
     */
    private boolean hasTimeField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        boolean flag = false;
        for (Field field : fields) {
            if (field.toString().contains("Time")) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            Class superClass = clazz.getSuperclass();
            for (Field field : superClass.getDeclaredFields()) {
                if (field.toString().contains("Time")) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            return true;
        }
        return false;
    }
}
