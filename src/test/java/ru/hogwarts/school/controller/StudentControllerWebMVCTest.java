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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

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
    }

    @Test
    void crud() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentRepository).save(ArgumentMatchers.any(Student.class));

    }
}
