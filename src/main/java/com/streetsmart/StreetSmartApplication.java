package com.streetsmart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreetSmartApplication {

	public static void main(String[] args) {
		loadDotenvIntoSystemProperties();
		SpringApplication.run(StreetSmartApplication.class, args);
	}

	private static void loadDotenvIntoSystemProperties() {
		Path envFile = Path.of(".env");
		if (!Files.exists(envFile)) {
			return;
		}

		try {
			List<String> lines = Files.readAllLines(envFile);
			for (String line : lines) {
				String trimmedLine = line.trim();
				if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
					continue;
				}

				int separatorIndex = trimmedLine.indexOf('=');
				if (separatorIndex < 1) {
					continue;
				}

				String key = trimmedLine.substring(0, separatorIndex).trim();
				String value = trimmedLine.substring(separatorIndex + 1).trim();
				if (key.isEmpty() || System.getenv(key) != null || System.getProperty(key) != null) {
					continue;
				}

				System.setProperty(key, stripOptionalQuotes(value));
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Failed to read .env file.", exception);
		}
	}

	private static String stripOptionalQuotes(String value) {
		if (value.length() >= 2) {
			char firstCharacter = value.charAt(0);
			char lastCharacter = value.charAt(value.length() - 1);
			if ((firstCharacter == '"' && lastCharacter == '"')
					|| (firstCharacter == '\'' && lastCharacter == '\'')) {
				return value.substring(1, value.length() - 1);
			}
		}

		return value;
	}

}
