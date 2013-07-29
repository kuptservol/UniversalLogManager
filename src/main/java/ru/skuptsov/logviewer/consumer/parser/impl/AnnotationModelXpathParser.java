package ru.skuptsov.logviewer.consumer.parser.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import ru.skuptsov.logviewer.annotation.XPATH;
import ru.skuptsov.logviewer.consumer.ConsumerHelper;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.consumer.parser.extractor.Extractor;
import ru.skuptsov.logviewer.consumer.parser.extractor.impl.XPathXmlExtractor;
import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;

public class AnnotationModelXpathParser<T> implements LogObjectParser {
	private static final Logger logger = Logger.getLogger(JavaAnnotationObjectParser.class);
	private final Extractor extractor;
	private final HashMap<FastMethod, String> objectPersistanceModel = new HashMap<FastMethod, String>();

	public AnnotationModelXpathParser(T model, Extractor extractor) throws Exception {
		logger.info("Added model: " + model.toString());
		readModel(model);
		if (extractor == null)
			throw new LogViewerConfigurationException("Extractor is empty");
		this.extractor = extractor;
		logger.info("Added model: " + extractor.getClass().getName());
	}

	private final void readModel(Object model) throws LogViewerConfigurationException {
		logger.info("ENTRY readModel");

		Class<?> modelClass = model.getClass();
		FastClass fc = FastClass.create(modelClass);

		for (Method method : modelClass.getMethods()) {
			XPATH annotation = method.getAnnotation(XPATH.class);
			if (annotation != null) {
				if (method.getName().startsWith("get")) {
					String setterMethodName = ConsumerHelper.getSetterMethodNameFromGetter(method);
					Class<?> returnType = method.getReturnType();

					FastMethod setterMethod = fc.getMethod(setterMethodName, new Class[] { returnType });
					if (setterMethod == null) {
						String message = "Can't find setter method for getter " + method.getName();
						logger.error(message);
						throw new LogViewerConfigurationException(message);
					}

					String xpathExpression = annotation.expression();

					objectPersistanceModel.put(setterMethod, xpathExpression);
				} else {
					String message = "XPATH annotation must be on getter";
					logger.error(message);
					throw new LogViewerConfigurationException(message);
				}
			}
		}

		logger.info("EXIT readModel");
	}

	@Override
	public Object parse(Object object) throws Exception {
		logger.info("ENTRY parse");
		Object messageObject = object.getClass().newInstance();

		try {

			if (!(object instanceof String)) {
				String message = "Parsing object must be a String type";
				logger.error(message);
				throw new LogViewerOperationException(message);
			}

			Document doc = ConsumerHelper.loadXMLFromString((String) object);

			XPathFactory factory = XPathFactory.newInstance();

			XPath xpath = factory.newXPath();

			for (Map.Entry<FastMethod, String> entry : objectPersistanceModel.entrySet()) {
				FastMethod setter = entry.getKey();
				String pattern = entry.getValue();

				Object fieldValue = XPathXmlExtractor.extract(pattern, doc, xpath, XPathConstants.STRING);

				setter.invoke(messageObject, new Object[] { fieldValue });
			}

		} catch (Exception e) {
			logger.error("Exception while parsing message object", e);
			throw new LogViewerOperationException(e);
		}

		logger.info("EXIT parse");
		return messageObject;

	}
}
