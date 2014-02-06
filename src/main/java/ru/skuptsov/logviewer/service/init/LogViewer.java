package ru.skuptsov.logviewer.service.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.persistance.bo.LogMessagePersisterBO;
import ru.skuptsov.logviewer.service.executor.LogMessagePersister;
import ru.skuptsov.logviewer.service.executor.impl.LogMessagePersisterImpl;

public class LogViewer {

	private static final AbstractApplicationContext context = new ClassPathXmlApplicationContext(
			"config/spring_config.xml");

	// TODO:
	@Autowired(required = true)
	private static LogMessagePersisterImpl messageLogger;

	public static AbstractApplicationContext getContext() {
		return context;
	}

	// TODO:
	@Autowired(required = true)
	private static LogObjectParser logObjectParser;

	// TODO:
	@Autowired(required = true)
	private static LogMessagePersisterBO logMessagePersister;

	public static LogMessagePersister getLogger() {
		return messageLogger;
	}

	public static LogObjectParser getParser() {

		return logObjectParser;
	}

	public static LogMessagePersisterBO getMessagePersister() {
		return logMessagePersister;
	}

	/*
	 * public static void setMessageLogger(LogMessagePersister messageLogger) {
	 * LogViewer.messageLogger = messageLogger; }
	 */

	public static LogObjectParser getLogObjectParser() {
		return logObjectParser;
	}

}
