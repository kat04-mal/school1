package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<String> getStudentsNamesStartsWithA() {
        logger.info("Was invoked method for names starts with A");

        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .toList();
    }

    public double getAverageAge() {
        logger.info("Was invoked method for average age");

        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public List<Student> getLastStudents() {
        logger.info("Was invoked method for last students");

        return studentRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Student::getId).reversed())
                .limit(5)
                .toList();
    }

    public long getSum() {
        logger.info("Was invoked method for sum (parallel stream)");

        return Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0L, Long::sum);
    }
}