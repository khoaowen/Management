package com.khoaowen.main.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.jdbcx.JdbcDataSource;

import com.khoaowen.utils.ExceptionHandler;

public class MyBatisConnectionFactory {

	private SqlSessionFactory sqlSessionFactory;
	private JdbcDataSource ds;

	public MyBatisConnectionFactory(String url) {
		ds = createDatasource(url);
		
		try {
			String resource = "mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (FileNotFoundException fileNotFoundException) {
			ExceptionHandler.showErrorAndLog(fileNotFoundException);
		} catch (IOException iOException) {
			ExceptionHandler.showErrorAndLog(iOException);
		}
	}


	/**
	 * create data source for connecting to
	 * @param path path to data source
	 * @return
	 */
	private JdbcDataSource createDatasource(String path) {
		JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:" + path + ";MV_STORE=FALSE;COMPRESS=TRUE");
        ds.setUser("khoaowen");
        ds.setPassword("khoaowen");
		return ds;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
	/**
	 * 
	 * @return @nullable
	 */
	public SqlSession openSession() {
		try {
			Connection conn = ds.getConnection();
			return sqlSessionFactory.openSession(conn);
		} catch (SQLException e) {
			ExceptionHandler.showErrorAndLog(e);
		}
		return null;
	}
}