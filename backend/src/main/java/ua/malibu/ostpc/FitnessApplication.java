package ua.malibu.ostpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ua.malibu.ostpc.config.DatabaseConfig;

@SpringBootApplication
@Import({DatabaseConfig.class})
@ComponentScan("ua.malibu.ostpc.controllers")
public class FitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessApplication.class, args);
	}
}
