package com.khoaowen.utils;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * LocalDate type handler for storing date with Mybatis 
 *
 */
@MappedTypes(LocalDate.class)
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType)
      throws SQLException {
	  LocalDateTime ldt = parameter.atTime(0,0);
	  ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
	  ps.setTimestamp(i,new Timestamp(zdt.toInstant().toEpochMilli()));
  }

  @Override
  public LocalDate getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    Timestamp sqlTimestamp = rs.getTimestamp(columnName);
    if (sqlTimestamp != null) {
    	return sqlTimestamp.toLocalDateTime().toLocalDate();
    }
    return null ;
  }

  @Override
  public LocalDate getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
    if (sqlTimestamp != null) {
    	return sqlTimestamp.toLocalDateTime().toLocalDate();
    }
    return null;
  }

  @Override
  public LocalDate getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
    if (sqlTimestamp != null) {
    	return sqlTimestamp.toLocalDateTime().toLocalDate();
    }
    return null;
  }
}
