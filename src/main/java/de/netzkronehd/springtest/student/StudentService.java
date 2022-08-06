package de.netzkronehd.springtest.student;

import de.netzkronehd.springtest.student.exceptions.StudentEmailNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Sort lowestAgeSort = Sort.by(Sort.Order.desc("birthday"));
    private static final Sort highestAgeSort = Sort.by(Sort.Order.asc("birthday"));

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public double getAverageAge() {
        long age = 0;
        final List<Student> students = getStudents();

        for(Student s : students) {
            age += s.getAge();
        }
        return age/(double)students.size();
    }

    public Student getLowestAge() {
        final List<Student> all = studentRepository.findAll(lowestAgeSort);
        if(all.size() >= 1) return all.get(0);
        return null;
    }

    public Student getHighestAge() {
        final List<Student> all = studentRepository.findAll(highestAgeSort);
        if(all.size() >= 1) return all.get(0);
        return null;
    }

    public void addNewStudent(Student student) {
        studentRepository.findStudentByEmail(student.getEmail()).ifPresentOrElse(student1 -> {
            throw new StudentEmailNotValidException("That E-Mail is already taken.");
        }, () -> studentRepository.save(student));

    }

    public void deleteStudent(Long id) {
        if(!studentRepository.existsById(id)) throw new IllegalStateException("Student '"+id+"' does not exists.");
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {

        final Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student '"+id+"' does not exists."));

        if(StringUtils.hasText(name) && !student.getName().equalsIgnoreCase(name)) {
            student.setName(name);
        }
        if(StringUtils.hasText(email) && !student.getEmail().equalsIgnoreCase(email)) {
            if(studentRepository.findStudentByEmail(email).isPresent()) {
                throw new StudentEmailNotValidException("That E-Mail is already taken.");
            }
            student.setEmail(email.trim());
        }
        studentRepository.saveAndFlush(student);
    }

    public Student getStudent(Long id) {
        final Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) throw new IllegalStateException("Student '"+id+"' does not exists.");

        return student.get();
    }
}
