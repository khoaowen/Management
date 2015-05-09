package com.khoaowen.main.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.khoaowen.main.mapper.PersonMapper;
import com.khoaowen.main.model.Person;

public class PersonDAO implements PersonMapper{
	
	private final MyBatisConnectionFactory myBatisConnectionFactory;
	
	public PersonDAO(MyBatisConnectionFactory myBatisConnectionFactory) {
		this.myBatisConnectionFactory = myBatisConnectionFactory;
	}
	

	@Override
	public Person getById(int id) {
		SqlSession session = myBatisConnectionFactory.openSession();
		try {
			PersonMapper personMapper = session.getMapper(PersonMapper.class);
			return personMapper.getById(id);
		} finally {
			session.close();
		}
		
	}

	@Override
	public void deleteById(int id) {
		SqlSession session = myBatisConnectionFactory.openSession();
		try {
			PersonMapper personMapper = session.getMapper(PersonMapper.class);
			personMapper.deleteById(id);
		} finally {
			session.close();
		}
	}

	@Override
	public int insert(Person person) {
		SqlSession session = myBatisConnectionFactory.openSession();
		try {
			PersonMapper personMapper = session.getMapper(PersonMapper.class);
			personMapper.insert(person);
			session.commit();
			return person.getId();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Person person) {
		SqlSession session = myBatisConnectionFactory.openSession();
		try {
			PersonMapper personMapper = session.getMapper(PersonMapper.class);
			personMapper.update(person);
			session.commit();
		} finally {
			session.close();
		}
	}


	@Override
	public List<Person> getAll() {
		SqlSession session = myBatisConnectionFactory.openSession();
		try {
			PersonMapper personMapper = session.getMapper(PersonMapper.class);
			return personMapper.getAll();
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<Person> lazyGetAll() {
		SqlSession session = myBatisConnectionFactory.openSession();
		try {
			PersonMapper personMapper = session.getMapper(PersonMapper.class);
			return personMapper.lazyGetAll();
		} finally {
			session.close();
		}
	}

}
