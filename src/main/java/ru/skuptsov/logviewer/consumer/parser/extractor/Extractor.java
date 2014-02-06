package ru.skuptsov.logviewer.consumer.parser.extractor;

public interface Extractor {
	
	
	Object extract(Object object, String pattern);

}
