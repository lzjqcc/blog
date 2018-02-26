package com.lzj.aop;

import com.lzj.constant.TYPEEnum;
import com.lzj.domain.BaseEntity;
import com.lzj.exception.SystemException;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.hamcrest.beans.PropertyUtil;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import com.lzj.annotation.EnableRelationTable;
import org.apache.ibatis.transaction.Transaction;

import javax.management.Descriptor;
import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 插入关联表
 */
@Aspect
@Component
public class InsertAop {
    private final Logger logger = LoggerFactory.getLogger(InsertAop.class);
    private static final String TABLE_NAME = "tableName";
    private static final String FIELD_VALUE = "fieldName";
    private static final String KEY_ROW = "keyRow";
    @Autowired
    private DataSource dataSource;

    @AfterReturning(value = "execution(* com.lzj.dao.*.insert*(*))")
    public void insertRelationTable(JoinPoint joinPoint) throws IllegalAccessException {
        Field invocationField = ReflectionUtils.findField(joinPoint.getClass(), "methodInvocation");
        invocationField.setAccessible(true);
        MethodInvocation invocation = (MethodInvocation) invocationField.get(joinPoint);
        Method method = invocation.getMethod();
        if (method != null) {
            EnableRelationTable relationTable = method.getAnnotation(EnableRelationTable.class);
            if (relationTable == null) {
                return;
            }
            BaseEntity entity = (BaseEntity) joinPoint.getArgs()[0];
            execInsert(relationTable, entity);
        }

    }

    private void execInsert(EnableRelationTable relationTable, BaseEntity entity) {
        SpringManagedTransaction transactional = new SpringManagedTransaction(dataSource);
        Connection connection = null;
        PreparedStatement statement = null;
        String SQL = null;
        try {
            connection = transactional.getConnection();
            SQL = buildSQL(entity);
            logger.info("插入关联表sql --->{}",SQL);
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
            logger.info("插入关联表成功sql --->{}",SQL);
            transactional.commit();
        } catch (IllegalAccessException e) {
            rollback(transactional);
            throw new SystemException(321, "插入关系表失败:SQL-->"+SQL,e );
        } catch (SQLException e) {
            rollback(transactional);
            throw new SystemException(321, "插入关系表失败:SQL-->"+SQL ,e);
        } catch (IntrospectionException e) {
            rollback(transactional);
            e.printStackTrace();
        } finally {

          /*  try {
                transactional.commit();
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                transactional.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

        }

    }

    private void rollback(Transaction transaction) {
        try {
            transaction.rollback();
        } catch (SQLException e) {
            try {
                transaction.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    private  Map<String, Object> parseFieldAnnotation(BaseEntity entity) throws IllegalAccessException, IntrospectionException {
        Map<String, Object> tableInfo = new LinkedHashMap<>();
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        Map<String, List> keyRowMap = new HashMap<>();
        tableInfo.put(KEY_ROW, keyRowMap);
        java.util.Date nowDate = new java.util.Date();// 取当前时间
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd"); // 转换时间格式
    ;
        fieldMap.put("create_time","current_timestamp()");
        fieldMap.put("update_time", "current_timestamp()");
        Class entityClass = entity.getClass();
        Method idMethod = PropertyUtils.getReadMethod(new PropertyDescriptor("id", entityClass));
        EnableRelationTable idRelation = idMethod.getAnnotation(EnableRelationTable.class);
        if (idRelation != null) {
            fieldMap.put(idRelation.value()[0],entity.getId());
        }
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Method method = PropertyUtils.getReadMethod(new PropertyDescriptor(field.getName(),entityClass));
            EnableRelationTable relationTable = method.getAnnotation(EnableRelationTable.class);
            if (relationTable == null) {
                continue;
            }
            if (!relationTable.keyRow()) {
                if (relationTable.type().name().equals(TYPEEnum.INTEGER.name())) {
                    fieldMap.put(relationTable.value()[0], field.get(entity));
                }
                if (relationTable.type().name().equals(TYPEEnum.STRING.name())) {
                    fieldMap.put(relationTable.value()[0], "'"+field.get(entity).toString()+"'");
                }

            }else {
                keyRowMap.put(relationTable.value()[0], (List) field.get(entity));
            }
            if (tableInfo.get(TABLE_NAME) == null) {
                tableInfo.put(TABLE_NAME, relationTable.relationTableName());
            }
        }
        tableInfo.put(FIELD_VALUE, fieldMap);
        return tableInfo;
    }
    private  String buildSQL(BaseEntity entity) throws IllegalAccessException, IntrospectionException {
        logger.info("开始构建sql -->{}",entity);
        Map<String, Object> map = parseFieldAnnotation(entity);
        Map fieldMap = (Map) map.get(FIELD_VALUE);
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ")
                .append(map.get(TABLE_NAME) +" (");
        Set<Map.Entry<String, Object>> entries = fieldMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            builder.append( entry.getKey() +",");
        }
        builder.deleteCharAt(builder.length()-1);
        Map keyRowMap = (Map) map.get(KEY_ROW);
        Set set = keyRowMap.keySet();
        set.stream().forEach( key ->builder.append(","+key));
        builder.append(") values ");
        builder.append(buildValues(keyRowMap, singleValue(fieldMap)));

        return builder.toString();
    }

    /**
     * (1,2,3,
     * @return
     */
    private  String singleValue(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        builder.append(" (");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            builder.append(entry.getValue()+",");
        }
        return builder.toString();
    }
    //
    private  String buildValues(Map map, String singleValue) {
        Set<Map.Entry<String,Object>> keyRowEntry = map.entrySet();
        List list = null;
        StringBuilder builder = new StringBuilder();
        String fieldName = null;
        for (Map.Entry<String,Object> entry : keyRowEntry) {
            list = (List) entry.getValue();
            fieldName = entry.getKey();
        }
        if (list == null) {
            throw new SystemException(120,"关联"+fieldName+"为null;插入keyRow信息"+map);
        }


        for (Object o : list) {
            builder.append(" "+singleValue);
            builder.append(o +"),");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
