package ru.skuptsov.logviewer;

import ru.skuptsov.logviewer.consumer.impl.SimpleLogObjectConsumer;
import ru.skuptsov.logviewer.test.model.*;

public class MAIN {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 1; i++)
			SimpleLogObjectConsumer.logMessage(new LogObjectModel());
	}

		
}



