package ru.skuptsov.logviewer.service.executor;


/**
 * The only entry point for logging capabilities 
 * @author skuptsov
 *
 */
public interface MessageLogger {

	boolean logMessage(Object message);
}
