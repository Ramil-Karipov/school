package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StudentControllerTest {

    @Autowired
    TestRestTemplate template;

    Faculty faculty;

    ObjectMapper objectMapper = new ObjectMapper();

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

        body.setAge(21);
        HttpEntity<Student> requestEntity = new RequestEntity<>(body, HttpMethod.PUT, null);
        response = template.exchange("/student/" + body.getId(), HttpMethod.PUT, requestEntity, Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        response = template.getForEntity("/student/" + body.getId(), Student.class);
        body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getName()).isEqualTo("stud_name");
        assertThat(body.getAge()).isEqualTo(21);

        template.delete("/student/" + body.getId());
        response = template.getForEntity("/student/" + body.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }

    @Test
    void filters() throws JsonProcessingException {
        Student student1 = createStudent(20);
        Student student2 = createStudent(25);
        Student student3 = createStudent(30);

        ResponseEntity<ArrayList> response = template.getForEntity("/student", ArrayList.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);

        response = template.getForEntity("/student/filtered?age=25", ArrayList.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        LinkedHashMap<String, Object> o = (LinkedHashMap<String, Object>) response.getBody().get(0);
        assertThat((Integer) o.get("id")).isEqualTo(student2.getId().intValue());

        ResponseEntity<String> jsonResponse = template.getForEntity("/student/filtered?min=24&max=34", String.class);
        assertThat(jsonResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(jsonResponse.getBody()).isNotNull();
        String json = jsonResponse.getBody();
        ArrayList<Student> students = objectMapper.readValue(json, new TypeReference<ArrayList<Student>>() {
        });
        assertThat(students.size()).isEqualTo(2);

        response = template.getForEntity("/student/by-faculty?facultyId=" + faculty.getId(), ArrayList.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);


    }

    private Student createStudent(int age) {
        Student request = new Student(null, "stud_name", age);
        request.setFaculty(faculty);
        ResponseEntity<Student> response = template.postForEntity("/student", request, Student.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        return response.getBody();
    }
}
