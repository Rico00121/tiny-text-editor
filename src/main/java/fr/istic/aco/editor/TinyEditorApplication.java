package fr.istic.aco.editor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the TinyEditor.
 * This class serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication
public class TinyEditorApplication {
	/**
	 * The main method to run the TinyEditor application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TinyEditorApplication.class, args);
	}
}
