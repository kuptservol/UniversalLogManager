package ru.skuptsov.logviewer.consumer.parser.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import ru.skuptsov.logviewer.annotation.XPATH;
import ru.skuptsov.logviewer.consumer.ConsumerHelper;
import ru.skuptsov.logviewer.consumer.ModelExpressionReader;
import ru.skuptsov.logviewer.consumer.parser.AbstractExpressionParser;
import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.consumer.parser.extractor.Extractor;
import ru.skuptsov.logviewer.consumer.parser.extractor.impl.XPathXmlExtractor;
import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;

public class XpathAnnotationModelParser<T> extends AbstractExpressionParser<T> {
	private static final Logger logger = Logger
			.getLogger(XpathAnnotationModelParser.class);

	private final HashMap<FastMethod, XPathExpression> objectPersistanceModel = new HashMap<FastMethod, XPathExpression>();

	public XpathAnnotationModelParser(T model)
			throws LogViewerConfigurationException {
		logger.info("Added model: " + model.toString());

		try {

			ConsumerHelper.readModel(model, objectPersistanceModel,
					XPATH.class, new ModelExpressionReader<XPathExpression>() {

						@Override
						public XPathExpression getExpression(
								Annotation annotation)
								throws XPathExpressionException {
							XPATH xpathAnn = (XPATH) annotation;
							String xpathExpr = xpathAnn.expression();

							XPathFactory factory = XPathFactory.newInstance();

							XPath xpath = factory.newXPath();

							return xpath.compile(xpathExpr);
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

		XPathExpression expression = (XPathExpression) pattern;

		return expression.evaluate((Document) addObjects.get("doc"),
				(QName) addObjects.get("qType"));
	}

	@Override
	// TODO: throw Exception- ?
	protected Map<String, Object> getAddObjects(Object object) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Document doc = ConsumerHelper.loadXMLFromString((String) object);

		map.put("doc", doc);
		map.put("qType", XPathConstants.STRING);

		return map;
	}

	@Override
	protected Map<FastMethod, ?> getPersistanceModel() {
		return objectPersistanceModel;
	}

	@Override
	protected void validate(Object logObject) throws Exception {

		if (!(logObject instanceof String)) {
			String message = "Parsing object must be of String type";
			logger.error(message);
			throw new LogViewerOperationException(message);
		}

	}

}
