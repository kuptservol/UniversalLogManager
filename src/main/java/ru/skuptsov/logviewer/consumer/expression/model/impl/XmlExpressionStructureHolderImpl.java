package ru.skuptsov.logviewer.consumer.expression.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import ru.skuptsov.logviewer.consumer.expression.model.XmlExpressionStructureHolder;
import ru.skuptsov.logviewer.service.init.LogViewer;

public class XmlExpressionStructureHolderImpl implements
		XmlExpressionStructureHolder {

	@Autowired
	private ApplicationContext appContext;

	Map<String, String> expressionMethodMap = new HashMap<String, String>();

	public XmlExpressionStructureHolderImpl(String xmlResourcePath) {

		Resource xmlResource = appContext.getResource(xmlResourcePath);
		
		//xmlResource.getInputStream()

		expressionMethodMap.put("$.logObject.messageId", "getMessageID");
		expressionMethodMap
				.put("$.logObject.mediationName", "getMediationName");

	}

	@Override
	public Map<String, String> getExpressionMethodMap() {
		return expressionMethodMap;
	}

}
