package com.lzj.aop;

import com.lzj.domain.BaseEntity;
import com.lzj.exception.SystemException;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.hamcrest.beans.PropertyUtil;
import org.mybatis.spring.transaction.SpringManagedTransaction;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Aspect
@Component
public class InsertAop {
    private static final String TABLE_NAME = "tableName";
    private static final String FIELD_VALUE = "fieldName";
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
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (IllegalAccessException e) {
            rollback(transactional);
            throw new SystemException(321, "插入关系表失败:SQL-->"+SQL,e );
        } catch (SQLException e) {
            rollback(transactional);
            throw new SystemException(321, "插入关系表失败:SQL-->"+SQL ,e);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } finally {

            try {
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
            }

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

    private static Map<String, Object> parseFieldAnnotation(BaseEntity entity) throws IllegalAccessException, IntrospectionException {
        Map<String, Object> tableInfo = new HashMap<>();
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        fieldMap.put("create_time", new Date());
        fieldMap.put("update_time", new Date());
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
            buildTableFieldMap(fieldMap,relationTable,field,entityClass,entity);
            if (tableInfo.get(TABLE_NAME) == null) {
                tableInfo.put(TABLE_NAME, relationTable.relationTableName());
            }
        }
        tableInfo.put(FIELD_VALUE, fieldMap);
        return tableInfo;
    }
    private static void buildTableFieldMap(Map<String,Object> fieldMap,EnableRelationTable relationTable, Field field,Class entityClass,BaseEntity entity) throws IntrospectionException, IllegalAccessException {

        if (relationTable == null) {
            return;
        }
        fieldMap.put(relationTable.value()[0], field.get(entity));

    }
    private static String buildSQL(BaseEntity entity) throws IllegalAccessException, IntrospectionException {
        Map<String, Object> map = parseFieldAnnotation(entity);
        Map fieldMap = (Map) map.get(FIELD_VALUE);
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ")
                .append(map.get(TABLE_NAME))
                .append(" (create_time,update_time");
        Set<Map.Entry<String, Object>> entries = fieldMap.entrySet();
        List list = null;
        String listName = null;
        for (Map.Entry<String, Object> entry : entries) {
            builder.append("," + entry.getKey());
            if (entry.getValue().getClass().getSimpleName().endsWith("List")){
                list = (List) entry.getValue();
                listName = entry.getKey();
            }
        }
        if (list == null) {
            throw new SystemException(120,"关联"+listName+"为null;插入关联表信息"+fieldMap);
        }
        builder.append(") values ");
        builder.append(buildValues(list, singleValue(fieldMap)));
        return builder.toString();
    }

    /**
     * (1,2,3,
     * @return
     */
    private static String singleValue(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        builder.append(" (");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (!entry.getValue().getClass().getSimpleName().endsWith("List")) {
                builder.append(entry.getValue()+",");
            }
        }
        return builder.toString();
    }
    //
    private static String buildValues(List list, String singleValue) {
        StringBuilder builder = new StringBuilder();

        for (Object o : list) {
            builder.append(" "+singleValue);
            builder.append(o +"),");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

}
