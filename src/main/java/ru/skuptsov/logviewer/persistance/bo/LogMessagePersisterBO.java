package ru.skuptsov.logviewer.persistance.bo;

/**
 * Business object interface for basic operations
 * 
 * @author skuptsov
 * 
 */
public interface LogMessagePersisterBO {

	boolean save(Object message);

	boolean delete(Object message);
}
