package com.lzj.aop;

import com.lzj.domain.BaseEntity;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import com.lzj.annotation.EnableRelationTable;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class InsertAop {
    @Autowired
    private DataSource dataSource;
    @AfterReturning(value = "execution(* com.lzj.dao.*.insert*(*))")
    public void insertRelationTable(JoinPoint joinPoint) throws IllegalAccessException {
        Signature signature = joinPoint.getSignature();
        Field invocationField = ReflectionUtils.findField(joinPoint.getClass(),"methodInvocation");
        invocationField.setAccessible(true);
        MethodInvocation invocation = (MethodInvocation) invocationField.get(joinPoint);
        Method method = invocation.getMethod();
        if (method != null) {
            EnableRelationTable relationTable = method.getAnnotation(EnableRelationTable.class);
            if (relationTable == null) {
                return;
            }
            BaseEntity entity = (BaseEntity) joinPoint.getArgs()[0];

        }

    }
    private void execInsert(EnableRelationTable relationTable, BaseEntity entity) throws SQLException {
        SpringManagedTransaction transactional = new SpringManagedTransaction(dataSource);
        Connection connection = transactional.getConnection();
        PreparedStatement statement = connection.prepareStatement("");
    }
    private void parseFieldAnnotation(BaseEntity entity) throws IllegalAccessException {
        Map<String, Object> tableInfo = new HashMap<>();
        tableInfo.put("fieldName", new HashMap<>());
        Class entityClass = entity.getClass();
        Field[] fields = entityClass.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            EnableRelationTable relationTable = field.getAnnotation(EnableRelationTable.class);
            if (relationTable == null) {
                continue;
            }
            ((HashMap)(tableInfo.get("fieldName"))).put(relationTable.value()[0], field.get(entity));
            tableInfo.put("tableName", relationTable.relationTableName());
        }

    }

}
