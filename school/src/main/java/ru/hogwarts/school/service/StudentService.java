package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student");

        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no student with id={}", id);
                    return new NotFoundException("Student not found: " + id);
                });
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        getStudent(student.getId());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        Student student = getStudent(id);
        studentRepository.delete(student);
    }

    public List<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for get students by age");
        return studentRepository.findByAge(age);
    }

    public List<Student> getStudentsBetweenAge(int min, int max) {
        logger.info("Was invoked method for get students between age");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Was invoked method for get student faculty");
        return getStudent(studentId).getFaculty();
    }
}