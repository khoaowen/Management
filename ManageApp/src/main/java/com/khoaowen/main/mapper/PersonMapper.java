package com.khoaowen.main.mapper;

import java.util.List;

import com.khoaowen.main.model.Person;

public interface PersonMapper {

	/**
	 * 
	 * @return list of available people in database
	 */
	List<Person> getAll();
	Person getById(int id);
	void deleteById(int id);
	int insert(Person person);
	void update(Person person);
}
