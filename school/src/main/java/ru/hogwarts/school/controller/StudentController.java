package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public List<Student> byAge(@RequestParam int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/age-between")
    public List<Student> findStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max) {

        return studentService.getStudentsBetweenAge(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }
}