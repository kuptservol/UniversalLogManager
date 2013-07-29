package ru.skuptsov.logviewer.consumer;

import java.io.StringReader;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;
import ru.skuptsov.logviewer.exception.LogViewerOperationException;
import ru.skuptsov.logviewer.service.init.LogViewer;

public class ConsumerHelper {

	public static boolean saveMessageTemplate(Object object) throws Exception {
		try {
			return LogViewer.getLogger().logMessage(LogViewer.getParser().parse(object));
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

}
