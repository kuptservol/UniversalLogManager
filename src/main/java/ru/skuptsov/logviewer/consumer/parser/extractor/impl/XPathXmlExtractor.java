/**
 * 
 */
package ru.skuptsov.logviewer.consumer.parser.extractor.impl;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import ru.skuptsov.logviewer.consumer.parser.extractor.Extractor;

// TODO: Add Commentary
// TDOD: Merge with XPathFactory in synchronization manner
/**
 * @author skuptsov XPathFactory is not thread safe - that's why using it in
 *         method stack
 * 
 */
public class XPathXmlExtractor {

	public static Object extract(String pattern, Document doc, XPath xpath, QName type) {

		Object result = null;
		try {

			XPathExpression expression = xpath.compile(pattern);

			result = expression.evaluate(doc, type);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
