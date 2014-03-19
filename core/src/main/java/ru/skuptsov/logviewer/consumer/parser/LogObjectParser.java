package ru.skuptsov.logviewer.consumer.parser;

/**
 * Parsing custom objects returns object with business fields to persist
 * 
 * 
 */
public interface LogObjectParser {

	Object parse(Object logObject) throws Exception;
}
