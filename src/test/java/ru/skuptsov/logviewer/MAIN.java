package ru.skuptsov.logviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.skuptsov.logviewer.consumer.impl.SimpleLogObjectConsumer;
import ru.skuptsov.logviewer.test.model.*;

public class MAIN {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 1; i++)
			SimpleLogObjectConsumer
					.logMessage(readFile("/test/json_ex_1.json"));
	}

	private static String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				MAIN.class.getResourceAsStream(file)));

		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}
}
