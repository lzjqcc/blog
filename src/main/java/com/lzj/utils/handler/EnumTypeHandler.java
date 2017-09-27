package com.lzj.utils.handler;

import com.lzj.exception.SystemException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private Enum[] enums;
    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.enums = (Enum[])type.getEnumConstants();
            if (this.enums == null) {
                throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
            }
        }
    }
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        if (e!=null) {
            E role=getE(e.toString());
            if (role!=null) {
                preparedStatement.setString(i, e.toString());
                return;
            }
            throw new SystemException(100,"传入的值只能是:"+enums.toString());
        }
        preparedStatement.setString(i,null);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String role=resultSet.getString(s);
        return getE(role);
    }
    private E getE(String role){
        for (Enum en:enums){
            if (en.toString().equals(role)){
                return (E) en;
            }
        }
        return null;
    }
    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {

        return getE(resultSet.getString(i));
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
