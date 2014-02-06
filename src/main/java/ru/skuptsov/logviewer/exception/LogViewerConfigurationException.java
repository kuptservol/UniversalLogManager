package ru.skuptsov.logviewer.exception;

public class LogViewerConfigurationException extends Exception {

	public LogViewerConfigurationException(Exception e) {
		super(e);

	}

	public LogViewerConfigurationException(String string) {
		super(string);
	}

}
