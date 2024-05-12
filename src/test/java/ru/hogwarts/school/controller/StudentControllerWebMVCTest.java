package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebMVCTest {
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    StudentService studentService;

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    Optional<Student> optionalStudent;
    private Student student;

    @BeforeEach
    void init() {
        student = new Student(1L, "stud_name", 20);
        student.setFaculty(new Faculty(1L, "stud_name", "black"));
        optionalStudent = Optional.of(student);
        Mockito.when(studentRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(optionalStudent);
        Mockito.when(studentRepository.findAll())
                .thenReturn(List.of(student));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentRepository).save(ArgumentMatchers.any(Student.class));

    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/student/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentRepository).save(ArgumentMatchers.any(Student.class));

    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, "/student/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentRepository).delete(ArgumentMatchers.any(Student.class));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("stud_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("age").value("20"));

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("stud_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value("20"));

    }
}
