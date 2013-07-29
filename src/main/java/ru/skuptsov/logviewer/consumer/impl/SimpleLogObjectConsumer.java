package ru.skuptsov.logviewer.consumer.impl;

import org.apache.log4j.Logger;

import ru.skuptsov.logviewer.consumer.ConsumerHelper;
import ru.skuptsov.logviewer.consumer.LogObjectConsumer;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;

public class SimpleLogObjectConsumer implements LogObjectConsumer {

	private static final Logger logger = Logger
			.getLogger(SimpleLogObjectConsumer.class);


	public static boolean logMessage(Object object) {
		logger.info("ENTRY logMessage " + object);
		boolean result=false;
		try {
			result = ConsumerHelper.saveMessageTemplate(object);
		} catch (Exception e) {
			logger.error("Exception while logging message Cause:"+e.toString());
		}

		logger.info("EXIT logMessage result: " + result);

		return result;
	}
}
