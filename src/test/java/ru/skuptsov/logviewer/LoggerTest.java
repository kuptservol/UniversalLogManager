package ru.skuptsov.logviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.service.executor.MessageLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LoggerTest {

	@Autowired(required = true)
	private MessageLogger messageLogger;

	@Autowired(required = true)
	private LogObjectParser logObjectParser;

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;

	@Repeat(value = 10)
	@Test
	public void test() throws Exception {
		messageLogger.logMessage(logObjectParser
				.parse(readFile("/test/json_ex_1.json")));
	}

	private static String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				LoggerTest.class.getResourceAsStream(file)));

		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}

	@After
	public void checkData() {
		Assert.assertEquals(jdbcTemplate
				.queryForInt("select count(*) from MSGLOG where mediationname='NF_CardInformationService'"), 9);
	}

}
