package com.lzj.interceptor;

import com.lzj.annotation.EnableRelationTable;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class RelationTableInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            Field delegateField = ReflectionUtils.findField(handler.getClass(), "delegate");
            delegateField.setAccessible(true);
            StatementHandler delegate = (StatementHandler) delegateField.get(handler);
            BoundSql boundSql = delegate.getBoundSql();
            String sqlType = boundSql.getSql().split(" ")[0];
            if (sqlType.equalsIgnoreCase("insert")) {
                Field mappedStatementField = ReflectionUtils.findField(delegate.getClass(), "mappedStatement");
                mappedStatementField.setAccessible(true);
                MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getField(mappedStatementField, delegate);
                // 包名+类名+方法名
                String mybatisId = mappedStatement.getId();
                String methodName = mybatisId.substring(mybatisId.lastIndexOf('.'));
                String packageClassName = mybatisId.substring(0, mybatisId.lastIndexOf('.'));
                Class daoClass = Class.forName(packageClassName);
                EnableRelationTable relationTable = ReflectionUtils.findMethod(daoClass, methodName).getAnnotation(EnableRelationTable.class);
                if (relationTable != null) {
                  String tableName =  relationTable.relationTableName();
                  Connection connection = (Connection) invocation.getArgs()[0];

                }
            }
        }
        return null;
    }
    private void executeInsertRelation(Connection connection) {

    }
    @Override
    public Object plugin(Object o) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
