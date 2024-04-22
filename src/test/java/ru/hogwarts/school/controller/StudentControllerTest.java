package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

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
        Student request = new Student(null, "stud_name", 20);
        request.setFaculty(faculty);
        ResponseEntity<Student> response = template.postForEntity("/student", request, Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Student body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getName()).isEqualTo("stud_name");
        assertThat(body.getAge()).isEqualTo(20);

        response = template.getForEntity("/student/" + body.getId(), Student.class);
        body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getName()).isEqualTo("stud_name");
        assertThat(body.getAge()).isEqualTo(20);


        HttpEntity<Student> requestEntity = new RequestEntity<>(body, HttpMethod.PUT, null);
        ResponseEntity<Student> exchange = template.exchange("/student/" + body.getId(), HttpMethod.PUT, requestEntity, Student.class);


    }
}
