package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import au.com.goielts.SoftwareDevelopmentProjectApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SoftwareDevelopmentProjectApplication.class)
@WebAppConfiguration
public class SoftwareDevelopmentProjectApplicationTests {

	@Test
	public void contextLoads() {
	}

}
