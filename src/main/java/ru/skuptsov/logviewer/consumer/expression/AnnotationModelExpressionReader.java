package ru.skuptsov.logviewer.consumer.expression;

import java.lang.annotation.Annotation;

import javax.xml.xpath.XPathExpressionException;

import ru.skuptsov.logviewer.exception.LogViewerConfigurationException;

public interface AnnotationModelExpressionReader<EXPR> {

	EXPR getExpression(Annotation annotation) throws Exception;

}
