package ru.hogwarts.school.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FacultyControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void createFacultyTest() {
        Faculty f = new Faculty();
        f.setName("Gryffindor");
        f.setColor("Red");

        ResponseEntity<Faculty> response =
                restTemplate.postForEntity(url("/faculty"), f, Faculty.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Gryffindor", response.getBody().getName());
    }

    @Test
    void getFacultyTest() {
        Faculty f = new Faculty();
        f.setName("Hufflepuff");
        f.setColor("Yellow");

        Faculty created = restTemplate.postForObject(url("/faculty"), f, Faculty.class);

        ResponseEntity<Faculty> response =
                restTemplate.getForEntity(url("/faculty/" + created.getId()), Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(created.getName(), response.getBody().getName());
    }

    @Test
    void updateFacultyTest() {
        Faculty f = new Faculty();
        f.setName("Ravenclaw");
        f.setColor("Blue");

        Faculty created = restTemplate.postForObject(url("/faculty"), f, Faculty.class);

        created.setColor("Dark Blue");

        HttpEntity<Faculty> entity = new HttpEntity<>(created);

        ResponseEntity<Faculty> response =
                restTemplate.exchange(url("/faculty"), HttpMethod.PUT, entity, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dark Blue", response.getBody().getColor());
    }

    @Test
    void deleteFacultyTest() {
        Faculty f = new Faculty();
        f.setName("Slytherin");
        f.setColor("Green");

        Faculty created = restTemplate.postForObject(url("/faculty"), f, Faculty.class);

        restTemplate.delete(url("/faculty/" + created.getId()));

        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/faculty/" + created.getId()), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getFacultyByColorTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/faculty?color=red"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void searchFacultyTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/faculty/search?value=gry"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getFacultyStudentsTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/faculty/1/students"), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful()
                || response.getStatusCode().is4xxClientError());
    }

    @Test
    void getFacultyNotFoundTest() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/faculty/999999"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}