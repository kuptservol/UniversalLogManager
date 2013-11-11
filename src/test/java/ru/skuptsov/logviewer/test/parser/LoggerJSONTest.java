package ru.skuptsov.logviewer.test.parser;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.skuptsov.logviewer.TestUtils;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.service.executor.MessageLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/applicationJSONTestContext.xml" })
public class LoggerJSONTest extends TestUtils{

	@Autowired(required = true)
	private MessageLogger messageLogger;

	@Autowired(required = true)
	private LogObjectParser logObjectParser;

	private SimpleJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Repeat(value = 1)
	@Test
	public void test() throws Exception {
		messageLogger.logMessage(logObjectParser
				.parse(readFile("/test/json_ex_1.json")));
	}

	@After
	public void checkData() {
		Assert.assertEquals(
				jdbcTemplate
						.queryForObject("select messageid from MSGLOG where mediationname='NF_CardInformationService'", String.class),
				"FES12324450056");
	}

}
