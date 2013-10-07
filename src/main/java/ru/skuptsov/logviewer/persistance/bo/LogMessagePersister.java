package ru.skuptsov.logviewer.persistance.bo;

/**
 * Business object interfacefor basic operations
 * @author skuptsov
 *
 */
public interface LogMessagePersister {
	
	boolean save(Object message);
	boolean delete(Object message);
}




