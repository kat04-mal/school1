package ru.hogwarts.school.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void createStudentTest() {
        Student s = new Student();
        s.setName("Harry");
        s.setAge(11);

        ResponseEntity<Student> response =
                restTemplate.postForEntity(url("/student"), s, Student.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry", response.getBody().getName());
    }

    @Test
    void getStudentTest() {
        Student s = new Student();
        s.setName("Ron");
        s.setAge(12);

        Student created = restTemplate.postForObject(url("/student"), s, Student.class);

        ResponseEntity<Student> response =
                restTemplate.getForEntity(url("/student/" + created.getId()), Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(created.getName(), response.getBody().getName());
    }

    @Test
    void updateStudentTest() {
        Student s = new Student();
        s.setName("Hermione");
        s.setAge(12);

        Student created = restTemplate.postForObject(url("/student"), s, Student.class);

        created.setAge(15);

        HttpEntity<Student> entity = new HttpEntity<>(created);

        ResponseEntity<Student> response =
                restTemplate.exchange(url("/student"), HttpMethod.PUT, entity, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(15, response.getBody().getAge());
    }

    @Test
    void deleteStudentTest() {
        Student s = new Student();
        s.setName("Draco");
        s.setAge(13);

        Student created = restTemplate.postForObject(url("/student"), s, Student.class);

        restTemplate.delete(url("/student/" + created.getId()));

        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/student/" + created.getId()), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getStudentsByAgeTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/student?age=11"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStudentsBetweenAgeTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/student/age-between?min=10&max=20"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStudentFacultyTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/student/1/faculty"), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful()
                || response.getStatusCode().is4xxClientError());
    }

    @Test
    void getStudentNotFoundTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/student/999999"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}