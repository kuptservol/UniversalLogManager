package ru.skuptsov.logviewer.exception;

public class LogViewerOperationException extends Exception {

	public LogViewerOperationException(String message) {
		super(message);
	}
	
	public LogViewerOperationException(Exception ex) {
		super(ex);
	}
	

}
