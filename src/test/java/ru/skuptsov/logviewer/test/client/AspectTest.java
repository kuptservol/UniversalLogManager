package ru.skuptsov.logviewer.test.client;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.skuptsov.logviewer.TestUtils;
import ru.skuptsov.logviewer.client.aspect.LogObject;
import ru.skuptsov.logviewer.client.aspect.SimpleLogObjectClientAspect;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.service.executor.LogMessagePersister;
import ru.skuptsov.logviewer.test.model.HibernatePlainLogObjectModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/applicationPlainAspectTestContext.xml" })
public class AspectTest extends TestUtils {

	@Autowired
	private AspectTestBean aspectTest;

	private SimpleJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Test
	public void testMethodParameterLog() {
		HibernatePlainLogObjectModel logObject = new HibernatePlainLogObjectModel();
		logObject.setMessageID("PLAIN12345");
		logObject.setMediationName("NF_ContractInformationService");

		aspectTest.method("test", logObject);
	}

	@After
	public void checkData() {
		Assert.assertEquals(
				jdbcTemplate
						.queryForObject(
								"select messageid from MSGLOG where mediationname='NF_ContractInformationService'",
								String.class), "PLAIN12345");
	}

}
