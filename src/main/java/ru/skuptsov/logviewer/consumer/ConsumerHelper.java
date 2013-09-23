package ru.skuptsov.logviewer.consumer;

import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;
import ru.skuptsov.logviewer.service.init.LogViewer;

public class ConsumerHelper {

	private static final Logger logger = Logger.getLogger(ConsumerHelper.class);

	public static boolean logMessage(Object object) throws Exception {
		try {
			return LogViewer.getLogger().logMessage(
					LogViewer.getParser().parse(object));
		} catch (Exception e) {

			throw new LogViewerOperationException(e);

		}
	}

	public static String getSetterMethodNameFromGetter(Method method) {
		String fieldName = method.getName();
		char[] array = fieldName.toCharArray();
		array[3] = Character.toUpperCase(array[3]);
		array[0] = 's';
		array[1] = 'e';
		array[2] = 't';

		return new String(array);
	}

	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public static <ANN extends Annotation, EXPR> void readModel(Object model,
			Map<FastMethod, EXPR> objectPersistanceModel,
			Class<ANN> annotationClass,
			ModelExpressionReader<EXPR> expReader)
			throws Exception {
		logger.info("ENTRY readModel");

		Class<?> modelClass = model.getClass();
		FastClass fc = FastClass.create(modelClass);

		for (Method method : modelClass.getMethods()) {
			ANN annotation = method.getAnnotation(annotationClass);
			if (annotation != null) {
				if (method.getName().startsWith("get")) {
					String setterMethodName = getSetterMethodNameFromGetter(method);
					Class<?> returnType = method.getReturnType();

					FastMethod setterMethod = fc.getMethod(setterMethodName,
							new Class[] { returnType });
					if (setterMethod == null) {
						String message = "Can't find setter method for getter "
								+ method.getName();
						logger.error(message);
						throw new LogViewerConfigurationException(message);
					}

					EXPR expression = expReader.getExpression(annotation);
					//TODO: add Validation

					objectPersistanceModel.put(setterMethod, expression);
				} else {
					String message = annotationClass.getName()
							+ " annotation must be on getter";
					logger.error(message);
					throw new LogViewerConfigurationException(message);
				}
			}
		}

		logger.info("EXIT readModel");
	}

}
