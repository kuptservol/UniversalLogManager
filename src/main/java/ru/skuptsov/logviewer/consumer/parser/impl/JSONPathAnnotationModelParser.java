package ru.skuptsov.logviewer.consumer.parser.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;

import ru.skuptsov.logviewer.annotation.JSON;
import ru.skuptsov.logviewer.annotation.XPATH;
import ru.skuptsov.logviewer.consumer.ConsumerHelper;
import ru.skuptsov.logviewer.consumer.expression.AnnotationModelExpressionReader;
import ru.skuptsov.logviewer.consumer.parser.AbstractExpressionParser;
import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;

public class JSONPathAnnotationModelParser<T> extends
		AbstractExpressionParser<T> {
	private static final Logger logger = Logger
			.getLogger(JSONPathAnnotationModelParser.class);

	private final HashMap<FastMethod, String> objectPersistanceModel = new HashMap<FastMethod, String>();

	public JSONPathAnnotationModelParser(T model)
			throws LogViewerConfigurationException {
		logger.info("Added model: " + model.toString());

		try {

			ConsumerHelper.readAnnotatedModel(model, objectPersistanceModel,
					JSON.class, new AnnotationModelExpressionReader<String>() {

						@Override
						public String getExpression(Annotation annotation)
								throws XPathExpressionException {
							JSON jSonAnn = (JSON) annotation;

							return jSonAnn.expression();
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
