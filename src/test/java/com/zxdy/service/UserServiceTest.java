package com.zxdy.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxdy.domain.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserServiceTest {

	@Autowired
	private UserService userService;
	@Test
	public void testHasMatchUser() {
		boolean matchCount=userService.hasMatchUser("zxdy", "123456");
		assertEquals(matchCount,true);
	}

	@Test
	public void testFindUserByUserNmae() {
		User user=userService.findUserByUserNmae("mkyong");
		assertEquals(user.getUserName(),"mkyong");
	}

	@Test
	public void testLoginSuccess() {
		fail("Not yet implemented");
	}

}
