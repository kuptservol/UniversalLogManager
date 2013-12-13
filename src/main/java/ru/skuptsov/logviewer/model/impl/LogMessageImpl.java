package ru.skuptsov.logviewer.model.impl;

import java.util.*;

import ru.skuptsov.logviewer.model.LogField;
import ru.skuptsov.logviewer.model.LogMessage;

public class LogMessageImpl{

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private String tableName;
	private List<LogField> logObjectsList;


	public Iterator<LogField> getLogObjectsIterator() {
		return logObjectsList.iterator();
	}


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setLogObjectsList(List<LogField> logObjectsList) {
		this.logObjectsList = logObjectsList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (logObjectsList != null)
			for (LogField logField : logObjectsList) {
				sb.append(logField.toString());
				sb.append(LINE_SEPARATOR);

			}
		return "LogMessageImpl [tableName=" + tableName + ", logObjectsList="
				+ sb.append(LINE_SEPARATOR) + "" + sb
				+ "]";
	}
}
