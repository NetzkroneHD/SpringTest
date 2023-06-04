package de.netzkronehd.springtest.student;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Configuration
public class StudentConfig {

    private final Logger logger;

    private final StudentRepository studentRepository;

    @Autowired
    public StudentConfig(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        logger = Logger.getLogger(getClass().getName());

    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            try {
                final long before = System.currentTimeMillis();

                final String json = getWebsiteData(new URL("https://names.drycodes.com/5"));

                final List<String> names = new ArrayList<>();
                final List<Student> students = new LinkedList<>();

                final String[] array = new Gson().fromJson(json, String[].class);
                logger.info("Loading "+array.length+" Students...");


                for(String name : array) {
                    if(!names.contains(name) && studentRepository.findStudentByName(name).isEmpty()) {
                        names.add(name);
                    }
                }

                final Random random = new Random();

                for (String name : names) {
                    Student student = new Student(name, name + "@gmail.com", LocalDate.of(random.nextInt(1950, 2015), random.nextInt(1, 12), random.nextInt(1, 25)));
                    students.add(student);
                }

                studentRepository.saveAll(students);
                logger.info("Finished loading "+students.size()+" Students after "+((System.currentTimeMillis()-before)/1000)+"ms");
            } catch (Exception ignored) {}

        };
    }

    private String getWebsiteData(URL url) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        final StringBuilder sb = new StringBuilder();
        final char[] data = new char[5000];

        int count;
        while ((count = br.read(data)) != -1) {
            sb.append(data, 0, count);
        }
        br.close();
        return sb.toString();
    }

}
