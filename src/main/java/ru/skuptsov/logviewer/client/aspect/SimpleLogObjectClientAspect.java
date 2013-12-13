package ru.skuptsov.logviewer.client.aspect;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.service.executor.LogMessagePersister;

@Component
@Aspect
public class SimpleLogObjectClientAspect {

	Logger logger = Logger.getLogger(SimpleLogObjectClientAspect.class);

	@Autowired(required = true)
	private LogMessagePersister messageLogger;

	@Autowired(required = true)
	private LogObjectParser logObjectParser;

	private SimpleJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Around("@annotation(logObject)")
	public Object tryToLogBefore(final ProceedingJoinPoint pjp,
			final LogObject logObject) throws Throwable {

		try {
			int logMethodArgument = logObject.logMethodArgument();

			if (logMethodArgument != 0) {

				Object[] methodArguments = pjp.getArgs();
				if (methodArguments != null && methodArguments.length != 0
						&& logMethodArgument <= methodArguments.length) {
					messageLogger.persistLogMessage(logObjectParser
							.parse(methodArguments[logMethodArgument - 1]));

				}
			}
		} catch (Exception ex) {
			logger.info("Error while logging object", ex);
		}

		Object returnObject = pjp.proceed();

		if (logObject.logReturnObject() && returnObject != null) {
			messageLogger
					.persistLogMessage(logObjectParser.parse(returnObject));
		}

		return returnObject;
	}
}
