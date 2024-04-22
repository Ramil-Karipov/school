package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import static org.assertj.core.api.Assertions.*;
import ru.hogwarts.school.service.FacultyService;

@SpringBootTest (classes = SchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StudentControllerTest {

    @Autowired
    TestRestTemplate template;

    Faculty faculty;

    @BeforeEach
    void init() {
        Faculty faculty = new Faculty(null, "math", "black");
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        this.faculty = response.getBody();
    }

    @Test
    void crud() {
        Studet request = new Student (null,"stud_name",20);
        studet.setFaculty(faculty);
        ResponseEntity<Student> response = template.postForEntity("/student", studet, Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Student body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getName()).isEqualTo("stud_name");
        assertThat(body.getAge()).isEqualTo(20);


    }
}
