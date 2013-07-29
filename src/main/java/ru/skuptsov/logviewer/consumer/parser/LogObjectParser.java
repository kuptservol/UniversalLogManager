package ru.skuptsov.logviewer.consumer.parser;


/**
 * Parsing custom objects returns object with business
 * fields to persist
 * @author skuptsov
 *
 */
public interface LogObjectParser {

	Object parse(Object object) throws Exception;
}
