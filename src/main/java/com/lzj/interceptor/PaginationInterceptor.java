package com.lzj.interceptor;

import com.lzj.domain.Page;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
@Component
public class PaginationInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            Field delegateField = ReflectionUtils.findField(handler.getClass(), "delegate");
            delegateField.setAccessible(true);
            StatementHandler delegate = (StatementHandler) delegateField.get(handler);
            Field mappedStatementField = ReflectionUtils.findField(delegate.getClass(), "mappedStatement");
            mappedStatementField.setAccessible(true);
            BoundSql boundSql = delegate.getBoundSql();
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) boundSql.getParameterObject();
            Set<Map.Entry> entries = paramMap.entrySet();
            if (boundSql != null) {
                for (Map.Entry entry : entries) {
                    if (entry.getValue() instanceof Page) {
                        Page page = (Page) entry.getValue();
                        if (boundSql != null && !boundSql.getSql().contains("limit")) {
                            Connection connection = (Connection) invocation.getArgs()[0];
                            Field sqlField = ReflectionUtils.findField(BoundSql.class, "sql");
                            sqlField.setAccessible(true);
                            String sql = (String) sqlField.get(boundSql);
                            count(sql,connection,page);
                            sql = getPageSql(sql,page);
                            sqlField.set(boundSql,sql);


                        }
                    }
                }

            }
        }
    /*    if (invocation.getTarget() instanceof CachingExecutor) {
            Object [] args = invocation.getArgs();
            MappedStatement statement = (MappedStatement) args[0];
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) args[1];
            BoundSql boundSql = statement.getBoundSql(args[1]);
            Set<Map.Entry> entries = paramMap.entrySet();
            for (Map.Entry entry : entries) {
                if (entry.getValue() instanceof  Page) {
                    Page page = (Page) entry.getValue();
                    if (boundSql != null && !boundSql.getSql().contains("limit")) {
                        Field sqlField = ReflectionUtils.findField(BoundSql.class,"sql");
                        sqlField.setAccessible(true);
                        String sql = (String) sqlField.get(boundSql);
                        sql = getPageSql(sql,page);
                        sqlField.set(boundSql,sql);
                        Field fieldSqlSource = ReflectionUtils.findField(MappedStatement.class, "sqlSource");
                        fieldSqlSource.setAccessible(true);
                        fieldSqlSource.set(statement,new MySqlSource(boundSql));
                    }
                }
            }

        }*/
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return   Plugin.wrap(target, this);
    }
    private String getPageSql(String sql, Page page) {
        // 1，单表查询分页
       // 2，关联查询分页
        Integer startIndex = (page.getCurrentPage()-1)* page.getPageSize();
        page.setStartIndex(startIndex);
        String pageSql = null;
        if (sql !=null && page !=null) {
            if (!sql.contains("and")) {
                // select * from tb_account where id >=(select id from tb_account order by id limit startIndex,1) limit pageSize;
                pageSql = sql + " where id >= " +getSubSql(page,sql)+ " limit "+ page.getPageSize();
            }else if (!sql.contains("join")) {
                pageSql = sql + " and id >=" +getSubSql(page,sql) + " limit " +page.getPageSize();
            }else if (sql.contains("join")) {
                pageSql=sql + " and "+getTableAlias(sql)+".id >=" +getSubSql(page,sql)+" limit "+page.getPageSize();
            }

        }
        return pageSql;
    }
    private String getSubSql(Page page, String sql) {
        return " (select id from " + getTableName(sql) +" order by id limit " + page.getStartIndex() +",1) ";
    }
    private String getTableName(String sql) {
        String [] splitFrom = sql.split("from");
        String tableName = splitFrom[splitFrom.length-1].split(" ")[0];
        return tableName;
    }
    private String getTableAlias(String sql){
        String [] splitsFrom = sql.split("from");
        return splitsFrom[splitsFrom.length-1].split(" ")[1];
    }
    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties);
    }
    public static class MySqlSource implements SqlSource {
        private BoundSql boundSql = null;
        public MySqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
    private String getCountSql(String sql) {
        return "select count(1) from" +sql.split("from")[1];
    }
    private void count(String sql, Connection connection, Page page) throws SQLException {
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            statement = connection.prepareStatement(getCountSql(sql));
             set = statement.executeQuery();
            set.next();
            page.setCount(set.getInt("count(1)"));
        } catch (SQLException e) {
            throw e;
        }finally {
            if (statement != null){
                statement.close();
            }
            if (set != null) {
                set.close();
            }
        }
    }
}
