package ru.skuptsov.logviewer.consumer.expression.model.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.skuptsov.logviewer.consumer.expression.model.XmlExpressionStructureHolder;
import ru.skuptsov.logviewer.service.init.LogViewer;

public class XmlExpressionStructureHolderImpl implements
		XmlExpressionStructureHolder {

	@Autowired
	ApplicationContext applicationContext;

	private Map<String, String> expressionMethodMap = new HashMap<String, String>();

	private final String xmlResourcePath;

	private final static String searchPattern = "/mappings/mapping[@type='getter']";

	private final static XPath xpath = XPathFactory.newInstance().newXPath();

	private static XPathExpression xPathExpr;

	@PostConstruct
	public void fillExpressionMethodMap() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {

		Resource xmlConfig = applicationContext.getResource(xmlResourcePath);

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();

		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		Document xmlConfigDoc = builder.parse(xmlConfig.getInputStream());

		NodeList nodeList = (NodeList) xPathExpr.evaluate(xmlConfigDoc,
				XPathConstants.NODESET);

		for (int i = 0; i < nodeList.getLength(); i++) {
			String pattern = nodeList.item(i).getChildNodes().item(3)
					.getTextContent();
			// TODO: WTF????
			String getterName = nodeList.item(i).getChildNodes().item(1)
					.getTextContent();

			expressionMethodMap.put(pattern, getterName);
		}

	}

	public XmlExpressionStructureHolderImpl(String xmlResourcePath)
			throws XPathExpressionException {

		this.xmlResourcePath = xmlResourcePath;
		this.xPathExpr = xpath.compile(searchPattern);

	}

	@Override
	public Map<String, String> getExpressionMethodMap() {
		return expressionMethodMap;
	}

}
