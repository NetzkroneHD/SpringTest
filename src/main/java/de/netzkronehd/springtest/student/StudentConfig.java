package de.netzkronehd.springtest.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.logging.Logger;

@Configuration
public class StudentConfig {

    private final Logger logger;

    public StudentConfig() {
        logger = Logger.getLogger(getClass().getName());

    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            final long before = System.currentTimeMillis();

            Student daniel = new Student(
                    "Daniel",
                    "daniel@gmail.com",
                    LocalDate.of(2002, Month.JUNE, 5)
            );

            Student jonas = new Student(
                    "Jonas",
                    "jonas@gmail.com",
                    LocalDate.of(2000, Month.DECEMBER, 28)
            );

            studentRepository.saveAll(Arrays.asList(daniel, jonas));
            logger.info("Finished loading Students after "+((System.currentTimeMillis()-before)/1000)+"ms");

        };
    }

}
