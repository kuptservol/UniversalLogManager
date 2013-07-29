package ru.skuptsov.logviewer.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.jmx.snmp.Timestamp;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.model.LogField;
import ru.skuptsov.logviewer.model.LogMessage;
import ru.skuptsov.logviewer.model.impl.LogMessageImpl;

/**
 * Uses CCGI reflection to get information from bean
 * 
 * @author skuptsov
 * 
 */
public class TestObjectParser implements LogObjectParser {

	private Object model;
	private static final Logger logger = Logger
			.getLogger(TestObjectParser.class);

	public TestObjectParser(Object model) {
		logger.info("Added model: " + model.toString());
		this.model = model;

	}

	@Override
	public Object parse(Object object) throws Exception {
		logger.info("ENTRY parse");

		LogMessageImpl message = new LogMessageImpl();
		message.setTableName("MSGLOG");
		final LogField field1 = new LogField("TIMESTAMP", new Date());
		final LogField field2 = new LogField("MESSAGEID", new String("CSD6464687456"));
		final LogField field3 = new LogField("MEDIATIONNAME", new String(
				"NF_CardInformationServiceModule"));

		message.setLogObjectsList(new ArrayList<LogField>() {
			{
				add(field1);
				add(field2);
				add(field3);
			}
		});

		logger.info("EXIT parse");
		return message;

	}

}
