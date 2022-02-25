package de.netzkronehd.springtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.ApplicationAvailability;

@SpringBootApplication
public class SpringTestApplication implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringTestApplication.class);

	private final ApplicationAvailability availability;

	public static void main(String[] args) {
		SpringApplication.run(SpringTestApplication.class, args);
	}

	@Autowired
	public SpringTestApplication(ApplicationAvailability availability) {
		this.availability = availability;
	}

	@Override
	public void run(ApplicationArguments args) {
		log.info("Ready: {}, Live: {}", availability.getReadinessState(), availability.getLivenessState());

	}
}
