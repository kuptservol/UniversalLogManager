package ru.skuptsov.logviewer.service.init;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.persistance.bo.LogMessageBO;
import ru.skuptsov.logviewer.service.executor.MessageLogger;

public class LogViewer {

	private static final AbstractApplicationContext context = new ClassPathXmlApplicationContext(
			"config/spring_config.xml");
	static {
		context.registerShutdownHook();
	}
	private static final Logger logger = Logger.getLogger(LogViewer.class);

	private static ApplicationContext getContext() {
		return context;
	}

	public static MessageLogger getLogger() {
		return getBeanById("messageLogger", MessageLogger.class);
	}

	public static LogObjectParser getParser() {

		return getBeanById("logObjectParser", LogObjectParser.class);
	}

	public static LogMessageBO getMessageBO() {
		return getBeanById("logMessageBO", LogMessageBO.class);
	}

	private static <T> T getBeanById(String id, Class<T> class_) {
		try {
			Object bean = context.getBean(id);
			logger.info("getBeanById : " + bean.getClass());
			return (T) bean;
		} catch (BeansException e) {
			logger.error("Exception while getting bean type Cause: ", e);
		}
		return null;
	}
}
