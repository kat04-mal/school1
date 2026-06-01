package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty get(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping
    public List<Faculty> byColor(@RequestParam String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/search")
    public List<Faculty> findFaculty(@RequestParam String value) {
        return facultyService.findFaculty(value);
    }

    @GetMapping("/{id}/students")
    public Collection<Student> getStudents(@PathVariable Long id) {
        return facultyService.getFacultyStudents(id);
    }
}