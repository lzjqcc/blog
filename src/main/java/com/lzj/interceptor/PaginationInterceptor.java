package com.lzj.interceptor;

import com.lzj.domain.Page;
import com.lzj.exception.SystemException;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 允许拦截，Excutor,ParameterHandler,ResultSetHandler,StatementHandler
 */

@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
@Component
public class PaginationInterceptor implements Interceptor{
    private static Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            Field delegateField = ReflectionUtils.findField(handler.getClass(), "delegate");
            delegateField.setAccessible(true);
            StatementHandler delegate = (StatementHandler) delegateField.get(handler);
            ParameterHandler parameterHandler = delegate.getParameterHandler();
            BoundSql boundSql = delegate.getBoundSql();
            if (boundSql.getParameterObject() instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) boundSql.getParameterObject();
                Set<Map.Entry> entries = paramMap.entrySet();
                if (boundSql != null) {
                    for (Map.Entry entry : entries) {
                        if (entry.getValue() instanceof Page) {
                            Page page = (Page) entry.getValue();
                            setPageSql(page, boundSql, invocation, parameterHandler);
                        }
                    }

                }
            }else if (boundSql.getParameterObject() instanceof Page) {
                Page page = (Page) boundSql.getParameterObject();
                setPageSql(page,boundSql,invocation,  parameterHandler);
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
                        sql = buildPageSQL(sql,page);
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
    private void setPageSql(Page page,BoundSql boundSql, Invocation invocation, ParameterHandler handler) throws IllegalAccessException, SQLException {
        if (boundSql != null && !boundSql.getSql().contains("limit")) {
            Connection connection = (Connection) invocation.getArgs()[0];
            Field sqlField = ReflectionUtils.findField(BoundSql.class, "sql");
            sqlField.setAccessible(true);
            String sql = (String) sqlField.get(boundSql);
            count(sql,connection,page, handler);
            sql = buildPageSQL(sql,page);
            sqlField.set(boundSql,sql);


        }
    }
    @Override
    public Object plugin(Object target) {
        return   Plugin.wrap(target, this);
    }
    private String buildPageSQL(String sql, Page page) {
        // 1，单表查询分页
       // 2，关联查询分页
        Integer startIndex = (page.getCurrentPage()-1)* page.getPageSize();
        if (startIndex < 0) {
            throw new SystemException(210,"sql 分页 page.getStartIndex < 0, currentPage 必须 >=1");
        }
        page.setStartIndex(startIndex);
        String pageSql = null;
        if (sql !=null && page !=null ) {
            // 没有排序
            if (!StringUtils.containsIgnoreCase(sql,"order")) {
                pageSql =sql + getPageSQL(sql, page,false);
            }else {
                int i = StringUtils.indexOfIgnoreCase(sql, "order");
                pageSql = sql.substring(0, i) + getPageSQL(sql, page,true) + sql.substring(i) +  " limit "+ page.getPageSize();
            }
        }
        logger.info("分页sql ===》{}",pageSql);
        return pageSql;
    }
    private String getPageSQL(String sql, Page page, boolean hasOrder) {

        String pageSql = "";
        if (!hasOrder) {
            if (!StringUtils.containsIgnoreCase(sql,"and") && !StringUtils.containsIgnoreCase(sql,"join")) {
                // select * from tb_account where id >=(select id from tb_account order by id limit startIndex,1) limit pageSize;
                pageSql =  " where id >= " +getSubSql(page,sql)+ " limit "+ page.getPageSize();
            }else if (!StringUtils.containsIgnoreCase(sql,"join")) {
                pageSql =  " and id >=" +getSubSql(page,sql) + " limit " +page.getPageSize();
            }else if (StringUtils.containsIgnoreCase(sql,"join")) {
                pageSql=" and "+getTableAlias(sql)+".id >=" +getSubSql(page,sql)+" limit "+page.getPageSize();
            }
        }else {
            if (!StringUtils.containsIgnoreCase(sql,"and") && !StringUtils.containsIgnoreCase(sql,"join")) {
                // select * from tb_account where id >=(select id from tb_account order by id limit startIndex,1) limit pageSize;
                pageSql =  " where id >= " +getSubSql(page,sql);
            }else if (!StringUtils.containsIgnoreCase(sql,"join")) {
                pageSql =  " and id >=" +getSubSql(page,sql) ;
            }else if (StringUtils.containsIgnoreCase(sql,"join")) {
                pageSql=" and "+getTableAlias(sql)+".id >=" +getSubSql(page,sql);
            }
        }

        return pageSql;
    }
    private String getSubSql(Page page, String sql) {
        return " (select id from " + getTableName(sql) +" order by id limit " + page.getStartIndex() +",1) ";
    }
    private String getTableName(String sql) {
        String [] splitFrom = spiltIgnoreCase(sql,"from");
        String tableName = splitFrom[splitFrom.length-1].split(" ")[1];
        return tableName;
    }
    private String getTableAlias(String sql){
        String[] splitsFrom = spiltIgnoreCase(sql,"from");
        return splitsFrom[splitsFrom.length-1].split(" ")[2];
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

        return "select count(1) from " +removeIgnoreCase(sql,"from");
    }

    /**
     * 参数处理  使用mybatis 自带的DefaultParameterHandler
     * @param sql
     * @param connection
     * @param page
     * @param handler
     * @throws SQLException
     */
    private void count(String sql, Connection connection, Page page, ParameterHandler handler) throws SQLException {
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            String countSQL = getCountSql(sql);
            logger.info("countrysql  sql --->{}", countSQL);
            statement = connection.prepareStatement(countSQL);
             handler.setParameters(statement);
             set =statement.executeQuery();
             set.next();
            page.setCount(set.getInt("count(1)"));
        } catch (SQLException e) {
            logger.error("执行count sql 失败 sql --->{}, exception--->{}",sql,e);
        }finally {
            if (statement != null){
                statement.close();
            }
            if (set != null) {
                set.close();
            }
        }
    }

    /**
     * from abc
     *  abc
     * @param source
     * @param removeString
     * @return
     */
    public  static   String removeIgnoreCase(String source, String removeString){

        return source.replaceAll("(?i)"+removeString,"&&").split("&&")[1];
    }
    private String [] spiltIgnoreCase(String source, String splitString) {
        return source.replaceAll("(?i)"+splitString,"&&").split("&&");
    }
}
