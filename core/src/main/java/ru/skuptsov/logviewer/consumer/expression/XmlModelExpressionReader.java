package ru.skuptsov.logviewer.consumer.expression;

import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;


public interface XmlModelExpressionReader<EXPR> {

	EXPR getExpression(String expression) throws LogViewerConfigurationException;

}
