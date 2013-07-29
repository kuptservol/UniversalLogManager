package ru.skuptsov.logviewer.persistance.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;
import ru.skuptsov.logviewer.model.LogField;
import ru.skuptsov.logviewer.model.LogMessage;
import ru.skuptsov.logviewer.model.impl.LogMessageImpl;
import ru.skuptsov.logviewer.persistance.dao.LogMessageDAO;

public class JDBCTemplateDAO implements LogMessageDAO {

	private static DataSource dataSource;
	private static final Logger logger = Logger
			.getLogger(JDBCTemplateDAO.class);

	public void setDataSource(DataSource dataSource) {
		logger.info("Setting datasource");
		this.dataSource = dataSource;
	}

	// Lazy initialization in order to datasource initialization first
	private static class Template {
		static {
			logger.info("Constructing template");
		}
		private static SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(
				dataSource);
	}

	@Override
	public boolean save(Object logMessageObject) throws Exception {

		String method = "save";
		logger.info("ENTRY method " + method);
		
		LogMessageImpl logMessage = (LogMessageImpl)logMessageObject;

		if (logMessage == null || logMessage.getTableName() == null
				|| logMessage.getTableName().equals("")
				|| logMessage.getLogObjectsIterator() == null) {
			String exception_text = "Logging message in method save is null or"
					+ " has no table name or has null iterator";
			logger.error(exception_text);
			throw new LogViewerOperationException(exception_text);
		}

		logger.info("LOG_MESSAGE: " + logMessage.toString());

		Map<String, Object> parameters = new HashMap<String, Object>();

		StringBuilder dbFields = new StringBuilder();
		StringBuilder dbFieldLink = new StringBuilder();

		Iterator<LogField> iterator = logMessage.getLogObjectsIterator();
		while (iterator.hasNext()) {
			LogField field = iterator.next();
			if (field == null) {
				logger.warn("Logfield is empty, skipping it");
				continue;
			}
			String fieldName = field.getdBFieldName();
			if (fieldName == null || fieldName.equals("")) {
				logger.warn("Logfield has empty field name, skipping it");
				continue;
			}

			dbFields.append(fieldName + ",");
			dbFieldLink.append(":" + fieldName + ",");
			parameters.put(fieldName, field.getLogFieldObject());

		}

		if (dbFields.length() > 1)
		{   // remove last char ','
			dbFields.deleteCharAt(dbFields.length() - 1);
		}
		else
		{
			logger.warn("List of parameters is empty");
			return false;
		}
		if (dbFieldLink.length() > 1)
		{
			// remove last char ','
			dbFieldLink.deleteCharAt(dbFieldLink.length() - 1);
		}
		else
		{
			logger.warn("List of parameter values is empty");
			return false;
		}

		StringBuilder sql = new StringBuilder("INSERT INTO  "
				+ logMessage.getTableName() + "(" + dbFields + ") VALUES ("
				+ dbFieldLink + ")");

		logger.info("Executing sql: " + sql);

		try {
			Template.jdbcTemplate.update(sql.toString(), parameters);
		} catch (Exception e) {
			logger.error("Error while saving logging message Cause: ", e);
			throw new LogViewerOperationException(e);
		}

		logger.info("EXIT method " + method);
		return true;

	}

	@Override
	public boolean delete(Object object) throws Exception {
		return false;

	}

}
