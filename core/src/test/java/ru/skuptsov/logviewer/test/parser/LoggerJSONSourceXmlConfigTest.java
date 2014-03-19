package ru.skuptsov.logviewer.test.parser;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.skuptsov.logviewer.TestUtils;
import ru.skuptsov.logviewer.consumer.expression.model.XmlExpressionStructureHolder;
import ru.skuptsov.logviewer.consumer.expression.model.impl.XmlExpressionStructureHolderImpl;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.service.executor.LogMessagePersister;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/applicationJSONTestXmlConfContext.xml" })
public class LoggerJSONSourceXmlConfigTest extends TestUtils {

	@Autowired
	private LogMessagePersister messageLogger;

	@Autowired
	private LogObjectParser logObjectParser;

	private SimpleJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Repeat(value = 1)
	@Test
	public void test() throws Exception {
		messageLogger.persistLogMessage(logObjectParser
				.parse(readFile("/test_data/json_ex_2.json")));

	}

	@After
	public void checkData() {
		Assert.assertEquals(
				jdbcTemplate
						.queryForObject(
								"select mediationName from MSGLOG where messageId='FES12324450055'",
								String.class), "NF_CardInformationService");
	}

}
