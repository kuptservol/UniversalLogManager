package ru.skuptsov.logviewer.service.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.persistance.bo.LogMessagePersister;
import ru.skuptsov.logviewer.service.executor.MessageLogger;


public class LogViewer {

	private static final AbstractApplicationContext context = new ClassPathXmlApplicationContext(
			"config/spring_config.xml");

	// TODO:
	@Autowired(required = true)
	private static MessageLogger messageLogger;

	// TODO:
	@Autowired(required = true)
	private static LogObjectParser logObjectParser;

	// TODO:
	@Autowired(required = true)
	private static LogMessagePersister logMessagePersister;

	public static MessageLogger getLogger() {
		return messageLogger;
	}

	public static LogObjectParser getParser() {

		return logObjectParser;
	}

	public static LogMessagePersister getMessagePersister() {
		return logMessagePersister;
	}

	public static MessageLogger getMessageLogger() {
		return messageLogger;
	}

	public static void setMessageLogger(MessageLogger messageLogger) {
		LogViewer.messageLogger = messageLogger;
	}

	public static LogObjectParser getLogObjectParser() {
		return logObjectParser;
	}

}
