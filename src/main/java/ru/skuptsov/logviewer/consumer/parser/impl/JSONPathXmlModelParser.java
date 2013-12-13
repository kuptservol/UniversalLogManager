package ru.skuptsov.logviewer.consumer.parser.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ru.skuptsov.logviewer.consumer.ConsumerHelper;
import ru.skuptsov.logviewer.consumer.expression.XmlModelExpressionReader;
import ru.skuptsov.logviewer.consumer.expression.model.XmlExpressionStructureHolder;
import ru.skuptsov.logviewer.consumer.parser.AbstractExpressionParser;
import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;

import com.jayway.jsonpath.JsonPath;

public class JSONPathXmlModelParser<T> extends AbstractExpressionParser<T> {
	private static final Logger logger = Logger
			.getLogger(JSONPathXmlModelParser.class);

	private final HashMap<FastMethod, String> objectPersistanceModel = new HashMap<FastMethod, String>();

	@Autowired
	XmlExpressionStructureHolder xmlStructureHolder;

	public JSONPathXmlModelParser(T model)
			throws LogViewerConfigurationException {
		logger.info("Added model: " + model.toString());

		try {

			ConsumerHelper.readXmlConfiguratedModel(model,
					objectPersistanceModel, xmlStructureHolder,
					new XmlModelExpressionReader<String>() {

						@Override
						public String getExpression(String jsonExpression)
								throws LogViewerConfigurationException {
							return jsonExpression;
						}

					});
		} catch (Exception ex) {
			logger.error("Error while reading xpath annotation model. Cause: ",
					ex);
			throw new LogViewerConfigurationException(ex);
		}

		super.model = model;
	}

	// TODO: throw Exception - ?
	@Override
	protected Object extract(Object pattern, Map addObjects, Object logObject)
			throws XPathExpressionException {

		String expression = (String) pattern;

		return JsonPath.read((String) logObject, expression);
	}

	@Override
	protected Map<FastMethod, ?> getPersistanceModel() {
		return objectPersistanceModel;
	}

	@Override
	protected void validate(Object logObject) throws Exception {

		ConsumerHelper.validateObjectType(logObject, String.class);
	}
}