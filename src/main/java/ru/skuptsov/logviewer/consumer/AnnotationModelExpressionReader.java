package ru.skuptsov.logviewer.consumer;

import java.lang.annotation.Annotation;

public interface ModelExpressionReader<EXPR> {

	EXPR getExpression(Annotation annotation) throws Exception;

}
