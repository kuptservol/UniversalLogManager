package ru.skuptsov.logviewer.consumer.parser.impl;

import ru.skuptsov.logviewer.consumer.parser.LogObjectParser;

public class POJOSimpleParser implements LogObjectParser{

	@Override
	public Object parse(Object object) throws Exception {
		return object;
	}

}
