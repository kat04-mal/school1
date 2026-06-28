package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private static final Logger logger =
            LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method for get faculty");

        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no faculty with id={}", id);
                    return new NotFoundException("Faculty not found: " + id);
                });
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        getFaculty(faculty.getId());
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = getFaculty(id);
        facultyRepository.delete(faculty);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method for get faculties by color");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Faculty> findFaculty(String value) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository
                .findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(value, value);
    }

    public Collection<Student> getFacultyStudents(Long facultyId) {
        logger.info("Was invoked method for get faculty students");
        return getFaculty(facultyId).getStudents();
    }
}