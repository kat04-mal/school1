package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private long idCounter = 1;

    public Long createStudent(Student student) {
        student.setId(idCounter++);
        students.put(student.getId(), student);

        return student.getId();
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        students.put(student.getId(), student);

        return student;
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students.values()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}