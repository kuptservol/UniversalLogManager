package ru.skuptsov.logviewer.service.executor.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ru.skuptsov.logviewer.model.LogMessage;
import ru.skuptsov.logviewer.persistance.bo.LogMessagePersisterBO;
import ru.skuptsov.logviewer.service.executor.LogMessagePersister;
import ru.skuptsov.logviewer.service.init.LogViewer;

public class LogMessagePersisterImpl implements LogMessagePersister {

	// TODO:comments
	private static final int DEFAULT_POOL_SIZE = 10;
	private static String executorType;
	private static int poolSize;
	private static boolean isPooled = false;
	private boolean isAsync = false;
	private static final Logger logger = Logger
			.getLogger(LogMessagePersisterImpl.class);

	@Autowired
	private LogMessagePersisterBO logMessagePersister;

	private enum EXECUTOR_TYPE {
		FIXED("fixed"), CACHED("cached"), CUSTOM("custom");

		private String type;

		EXECUTOR_TYPE(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	/**
	 * Creating executor lazy
	 */
	private static class EXECUTOR {
		private static Executor executor = null;
		static {
			logger.info("ENTRY Initializing executor");
			if (LogMessagePersisterImpl.isPooled()) {
				if (executorType == null || executorType.equals("")) {
					executor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
				} else {
					if (executorType.equals(EXECUTOR_TYPE.FIXED.getType())) {
						if (poolSize == 0) {
							executor = Executors
									.newFixedThreadPool(DEFAULT_POOL_SIZE);
						} else {
							executor = Executors.newFixedThreadPool(poolSize);
						}
					} else if (executorType.equals(EXECUTOR_TYPE.CACHED
							.getType())) {
						executor = Executors.newCachedThreadPool();
					} else if (executorType.equals(EXECUTOR_TYPE.CUSTOM
							.getType())) {
						// TODO: add custom thread executor
					} else {
						throw new RuntimeException("Unknown type of executor");
					}
				}
			}
			logger.info("EXIT Initializing executor");
		}

	}

	public boolean persistLogMessage(final Object message) {
		logger.info("ENTRY logMessage " + message.toString());

		if (isAsync()) {
			if (isPooled()) {
				EXECUTOR.executor.execute(new Runnable() {

					@Override
					public void run() {
						logMessagePersister.save(message);

					}

				});
			} else {
				new Thread(new Runnable() {

					@Override
					public void run() {
						logMessagePersister.save(message);

					}
				}).start();
			}
		} else {
			return logMessagePersister.save(message);
		}

		logger.info("EXIT logMessage ");

		return true;
	}

	private boolean isAsync() {
		return isAsync;
	}

	private static boolean isPooled() {
		return isPooled;
	}

	public void setExecutorType(String executorType) {
		this.executorType = executorType;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public void setIsPooled(boolean isPooled) {
		this.isPooled = isPooled;
	}

	public void setIsAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

}
