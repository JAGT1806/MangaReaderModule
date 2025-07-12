package com.jagt.reader.bootstrap;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MangaReaderModuleApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(MangaReaderModuleApplication.class);

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

			if (dotenv.entries().isEmpty()) {
				LOGGER.warn(".env file not found or empty");
			} else {
				LOGGER.info(".env file loaded successfully");
			}
		} catch (Exception e) {
			LOGGER.error("Error loading .env file: {}", e.getMessage());
		}

		SpringApplication.run(MangaReaderModuleApplication.class, args);
	}

}
