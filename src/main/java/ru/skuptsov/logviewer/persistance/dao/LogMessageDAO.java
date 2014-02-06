package ru.skuptsov.logviewer.persistance.dao;

import ru.skuptsov.logviewer.model.LogMessage;

/**
 * Data Acess Object for persisting messages
 * @author skuptsov
 *
 */
public interface LogMessageDAO {

	public boolean save(final Object object) throws Exception;

	public boolean delete(Object object) throws Exception;

}
