package de.netzkronehd.springtest.student;

import de.netzkronehd.springtest.student.exceptions.StudentEmailNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new StudentEmailNotValidException("That E-Mail is already taken.");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if(!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student '"+id+"' does not exists");
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {

        Student student = studentRepository.findById(id).orElseThrow(() ->
           new IllegalStateException("Student '"+id+"' does not exists"));

        if(name != null && name.length() > 0 && !student.getName().equalsIgnoreCase(name)) {
            student.setName(name);
        }
        if(email != null && email.length() > 0 && !student.getEmail().equalsIgnoreCase(email)) {
            if(studentRepository.findStudentByEmail(email).isPresent()) {
                throw new StudentEmailNotValidException("That E-Mail is already taken.");
            }
            student.setEmail(name);
        }


    }

}
