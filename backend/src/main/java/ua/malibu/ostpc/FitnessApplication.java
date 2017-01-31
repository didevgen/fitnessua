package ua.malibu.ostpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ua.malibu.ostpc.controllers", "ua.malibu.ostpc.services", "ua.malibu.ostpc.daos",
				"ua.malibu.ostpc.utils.auth"})
public class FitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessApplication.class, args);
	}
}
