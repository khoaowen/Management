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
	private SqlSession sqlSession;
	private Connection connection;

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

	
	/**
	 * Always open one session. Create a new one if there's no session opened
	 * before
	 * 
	 * @return @nullable if problems
	 */
	public SqlSession openSession() {
		try {
			connection = ds.getConnection();
			if (sqlSession == null) {
				sqlSession = sqlSessionFactory.openSession(connection);
			}
			return sqlSession;
		} catch (SQLException e) {
			ExceptionHandler.showErrorAndLog(e);
		}
		return null;
	}
	
	/**
	 * 
	 * @return @nullable return the current connection to database 
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Close session
	 */
	public void closeSession() {
    	if (sqlSession != null) {
    		sqlSession.close();
    	}
	}
}