package ru.skuptsov.logviewer.consumer.parser;

import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;

import ru.skuptsov.logviewer.exception.LogViewerOperationException;

/**
 * Abstract parser, based on some expression, with wich you can get information
 * from log object
 * 
 * @param <T>
 */
public abstract class AbstractExpressionParser<T> implements LogObjectParser {

	private static final Logger logger = Logger
			.getLogger(AbstractExpressionParser.class);
	protected T model = null;


	@Override
	public Object parse(Object logObject) throws Exception {
		logger.info("ENTRY parse");
		Object messageObject = model.getClass().newInstance();

		try {

			validate(logObject);

			Map addObjects = getAddObjects(logObject);

			for (Entry<FastMethod, ?> entry : getPersistanceModel().entrySet()) {
				FastMethod setter = entry.getKey();

				Object fieldValue = extract(entry.getValue(), addObjects,
						logObject);

				logger.info("Extracted value: " + fieldValue);

				setter.invoke(messageObject, new Object[] { fieldValue });
			}

		} catch (Exception e) {
			logger.error("Exception while parsing message object", e);
			throw new LogViewerOperationException(e);
		}

		logger.info("EXIT parse");
		return messageObject;

	}

	protected abstract void validate(Object logObject) throws Exception;

	protected abstract Object extract(Object pattern, Map addObjects,
			Object logObject) throws Exception;

	/**
	 * add specific objects for extracting value from log object
	 * 
	 * @param logObject
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getAddObjects(Object logObject)
			throws Exception {

		return null;

	}

	// TODO: is there really need in ? or Object
	protected abstract Map<FastMethod, ?> getPersistanceModel()
			throws Exception;

}
