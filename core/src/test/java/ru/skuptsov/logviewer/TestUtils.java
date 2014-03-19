package ru.skuptsov.logviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.skuptsov.logviewer.test.parser.LoggerJSONSourceAnnConfigTest;

public class TestUtils {
	
	protected  String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				LoggerJSONSourceAnnConfigTest.class.getResourceAsStream(file)));

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
