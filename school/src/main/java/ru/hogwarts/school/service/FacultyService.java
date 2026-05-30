package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();

    private long idCounter = 1;

    public Long createFaculty(Faculty faculty) {
        faculty.setId(idCounter++);
        faculties.put(faculty.getId(), faculty);

        return faculty.getId();
    }

    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);

        return faculty;
    }

    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.values()
                .stream()
                .filter(faculty ->
                        faculty.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }
}