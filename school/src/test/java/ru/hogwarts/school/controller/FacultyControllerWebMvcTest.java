package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean FacultyService facultyService;

    @Test
    void createTest() throws Exception {
        Faculty f = new Faculty();
        f.setId(1L);
        f.setName("Gryffindor");

        Mockito.when(facultyService.createFaculty(any())).thenReturn(f);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(f)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    void getTest() throws Exception {
        Faculty f = new Faculty();
        f.setId(1L);
        f.setName("Gryffindor");

        Mockito.when(facultyService.getFaculty(1L)).thenReturn(f);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    void notFoundTest() throws Exception {
        Mockito.when(facultyService.getFaculty(99L))
                .thenThrow(new NotFoundException("Faculty not found: 99"));

        mockMvc.perform(get("/faculty/99"))
                .andExpect(status().isNotFound());
    }
}