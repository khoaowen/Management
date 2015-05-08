package com.khoaowen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.khoaowen.main.dao.MyBatisConnectionFactory;
import com.khoaowen.main.mapper.PersonMapper;
import com.khoaowen.main.model.Person;
import com.khoaowen.main.model.Sex;
import com.khoaowen.utils.DateUtil;
import com.khoaowen.utils.ExceptionHandler;

public class MapperTest {
	
	
	private SqlSession session;
	List<Person> lists = new ArrayList<>();

	@BeforeTest
	public void setUp() throws IOException, SQLException {

		MyBatisConnectionFactory factory = new MyBatisConnectionFactory(
				"C:\\Users\\owen\\Desktop\\test");
		session = factory.openSession();
		initData();
	}
	
	@AfterTest
	public void tearDown() {
		session.close();
	}
	
	private void initData() {
		session.update("createTable");
	}
	
	
	@Test(priority = 0)
	public void insertPerson() {
		
		PersonMapper personMapper = session.getMapper(PersonMapper.class);
		for (int i = 0; i < 3; ++i) {
			Person person = new Person();
			
			File file = new File("C:\\Users\\owen\\Desktop\\test.jpg"); //windows
			byte[] bFile = new byte[(int) file.length()];

			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(bFile);
				fileInputStream.close();
			} catch (Exception e) {
				ExceptionHandler.showErrorAndLog(e);
			}
			person.setImage(bFile);
			
			//Image image = new Image(new ByteArrayInputStream(bFile));
			
			person.setFirstName("Dai " + i);
			person.setLastName("Nguyen ngoc trang " + i);
			person.setEmail("tieuthukieukys@yahoo.com " + i);
			person.setSex(Sex.FEMALE);
			LocalDate date = LocalDate.of(2015, 05, 03);
			person.setReligiousDate(date);
			System.out.println("Date: " + DateUtil.format(date));
			personMapper.insert(person);
			lists.add(person);
			
		}
		
		

		Assert.assertEquals(3, personMapper.getAll().size());
		Assert.assertEquals("Dai 1", personMapper.getById(lists.get(1).getId()).getFirstName());
		Assert.assertEquals(LocalDate.of(2015, 05, 03), personMapper.getById(lists.get(1).getId()).getReligiousDate());
	}
	
	@Test(priority = 1)
	public void deletePerson() {
		PersonMapper personMapper = session.getMapper(PersonMapper.class);
		Person p = lists.get(0);
		personMapper.deleteById(p.getId());
		Assert.assertEquals("Dai 1", personMapper.getAll().get(0).getFirstName());
	}
	
	@Test(priority = 3)
	public void updatePerson() {
		PersonMapper personMapper = session.getMapper(PersonMapper.class);
		Person p = personMapper.getAll().get(1);
		String updatedName = "UPDateed name";
		p.setFirstName(updatedName);
		p.setSex(Sex.MALE);
		p.setReligiousDate(LocalDate.of(2015, 10, 10));
		personMapper.update(p);
		Assert.assertEquals(updatedName, personMapper.getAll().get(1).getFirstName());
	}
}
