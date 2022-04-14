package de.netzkronehd.springtest.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
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

        };
    }

}
