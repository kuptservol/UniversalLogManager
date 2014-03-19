package ru.skuptsov.logviewer.test.client;

import ru.skuptsov.logviewer.client.aspect.LogObject;
import ru.skuptsov.logviewer.test.model.HibernatePlainLogObjectModel;

public class AspectTestBean {

	@LogObject(logMethodArgument = 2)
	public void method(String string, HibernatePlainLogObjectModel logObject) {

		
	}

}
