package ru.skuptsov.logviewer.model;

public class LogField {

	private String dBFieldName;
	private Object logFieldObject;

	public LogField() {

	}

	public LogField(String dBFieldName, Object logFieldObject) {
		super();
		this.dBFieldName = dBFieldName;
		this.logFieldObject = logFieldObject;
	}

	public String getdBFieldName() {
		return dBFieldName;
	}

	public void setdBFieldName(String dBFieldName) {
		this.dBFieldName = dBFieldName;
	}

	public Object getLogFieldObject() {
		return logFieldObject;
	}

	public void setLogFieldObject(Object logFieldObject) {
		this.logFieldObject = logFieldObject;
	}

	@Override
	public String toString() {
		return "LogField [dBFieldName=" + dBFieldName + ", logFieldObject="
				+ logFieldObject + "]";
	}

}
