package busy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Application class. It runs the whole application with an embedded
 * web container, and enables auto configuration and dependency injection.
 * 
 * @author malkomich
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"busy"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
