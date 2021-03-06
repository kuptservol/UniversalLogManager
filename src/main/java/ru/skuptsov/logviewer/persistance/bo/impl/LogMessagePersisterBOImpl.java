package ru.skuptsov.logviewer.persistance.bo.impl;

import org.apache.log4j.Logger;

import ru.skuptsov.logviewer.model.LogMessage;
import ru.skuptsov.logviewer.persistance.bo.LogMessagePersisterBO;
import ru.skuptsov.logviewer.persistance.dao.LogMessageDAO;

public class LogMessagePersisterBOImpl implements LogMessagePersisterBO {

	private static final Logger logger = Logger.getLogger(LogMessagePersisterBOImpl.class);
	private final LogMessageDAO logMessageDAO;

	public LogMessagePersisterBOImpl(LogMessageDAO logMessageDAO) {
		this.logMessageDAO = logMessageDAO;
	}

	@Override
	public boolean save(Object message) {
		try {
			logMessageDAO.save(message);
		} catch (Exception e) {
			logger.error("Can't save message: " + message.toString()
					+ " due to exception: " + e.getMessage());
			return false;
		}
		return true;

	}

	@Override
	public boolean delete(Object message) {
		try {
			logMessageDAO.delete(message);
		} catch (Exception e) {
			// TODO Genarate delete strategy
			e.printStackTrace();
		}
		return true;

	}

}
