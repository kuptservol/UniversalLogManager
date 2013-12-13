package ru.skuptsov.logviewer.consumer.parser.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;
import ru.skuptsov.logviewer.model.LogField;
import ru.skuptsov.logviewer.model.LogMessage;
import ru.skuptsov.logviewer.model.impl.LogMessageImpl;

/**
 * Uses CGLIB reflection to get information from bean and invoke it's getters
 * 
 * @author skuptsov
 * 
 */
public class JavaAnnotationObjectParser<T> implements LogObjectParser {

	private static final Logger logger = Logger
			.getLogger(JavaAnnotationObjectParser.class);

	private final Map<String, FastMethod> objectPersistanceModel = new HashMap<String, FastMethod>();
	private String tableName = null;

	public JavaAnnotationObjectParser(T model)
			throws LogViewerConfigurationException {
		logger.info("Added model: " + model.toString());
		readModel(model);
	}

	/**
	 * Prevent someone to override readModel method because it's in constructor
	 * 
	 * @param model
	 * @throws LogViewerConfigurationException
	 */
	private final void readModel(Object model)
			throws LogViewerConfigurationException {
		logger.info("ENTRY readModel");

		Class<?> modelClass = model.getClass();
		FastClass fc = FastClass.create(modelClass);

		Table table = modelClass.getAnnotation(Table.class);
		if (table == null)
			throw new LogViewerConfigurationException(
					"Missed @Table configuration in model");

		String tableName = table.name();
		if (tableName == null || tableName.equals(""))
			throw new LogViewerConfigurationException(
					"@Table(name=\"\") field is empty in configuration  model");

		logger.info("Found table name: " + tableName);
		this.tableName = tableName;
		// TODO:Переделать на аннотацию по полям
		//TODO: Добавить фишки с @Id
		Method[] methods = modelClass.getDeclaredMethods();
		for (Method method : methods) {
			Column column = method.getAnnotation(Column.class);
			if (column != null) {
				String columnName = column.name();
				if (columnName == null || columnName.equals(""))
					throw new LogViewerConfigurationException(
							"@Column(name=\"\") field is empty in configuration  model");
				logger.info("Found column name: " + columnName);

				FastMethod fm = fc.getMethod(method);

				objectPersistanceModel.put(columnName, fm);

			}
		}

		logger.info("EXIT readModel");

	}

	@Override
	public Object parse(Object object) throws Exception {
		logger.info("ENTRY parse");

		LogMessageImpl logMessage = null;
		if (object == null)
			return null;
		if (objectPersistanceModel.isEmpty() || tableName == null) {
			String message = "Logging object is null or table name is null or annotation "
					+ "persistance object model is empty";
			logger.warn(message);
			throw new LogViewerOperationException(message);
		}

		try {

			logMessage = new LogMessageImpl();
			List<LogField> logFieldsList = new ArrayList<LogField>();

			for (Map.Entry<String, FastMethod> entry : objectPersistanceModel
					.entrySet()) {

				LogField logField = new LogField();

				logField.setdBFieldName(entry.getKey());
				logField.setLogFieldObject(entry.getValue().invoke((T) object,
						null));

				logFieldsList.add(logField);
			}

			logMessage.setTableName(tableName);
			logMessage.setLogObjectsList(logFieldsList);
		} catch (Exception e) {
			logger.error("Problem while logging message", e);
			throw new LogViewerOperationException(e);
		}

		logger.info("EXIT parse");
		return logMessage;

	}
}
