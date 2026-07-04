package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/names-a")
    public List<String> getNamesA() {
        return studentService.getStudentsNamesStartsWithA();
    }

    @GetMapping("/average-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last")
    public List<Student> getLastStudents() {
        return studentService.getLastStudents();
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        List<Student> students = studentRepository.findAll();

        if (students.size() < 6) {
            return;
        }

        // Основной поток
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        thread1.start();
        thread2.start();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();

        if (students.size() < 6) {
            return;
        }

        // Основной поток
        printName(students.get(0).getName());
        printName(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            printName(students.get(2).getName());
            printName(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            printName(students.get(4).getName());
            printName(students.get(5).getName());
        });

        thread1.start();
        thread2.start();
    }

    private synchronized void printName(String name) {
        System.out.println(name);
    }
}