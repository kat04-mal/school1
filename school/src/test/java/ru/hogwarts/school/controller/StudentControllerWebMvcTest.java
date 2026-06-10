package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean StudentService studentService;

    @Test
    void createTest() throws Exception {
        Student s = new Student();
        s.setId(1L);
        s.setName("Harry");

        Mockito.when(studentService.createStudent(any())).thenReturn(s);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Harry"));
    }

    @Test
    void getTest() throws Exception {
        Student s = new Student();
        s.setId(1L);
        s.setName("Harry");

        Mockito.when(studentService.getStudent(1L)).thenReturn(s);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"));
    }

    @Test
    void notFoundTest() throws Exception {
        Mockito.when(studentService.getStudent(99L))
                .thenThrow(new NotFoundException("Student not found: 99"));

        mockMvc.perform(get("/student/99"))
                .andExpect(status().isNotFound());
    }
}