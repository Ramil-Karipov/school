package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student findStudent(long id);

    Student updateStudent(long id, Student student);

    Student deleteStudent(long id);

    Collection<Student> getStudentsByAge(int age);

    Collection<Student> getAllByAgeBetween(int min, int max);

    Collection<Student> getAll();

    long count();

    double getAverageAge();

    List<Student> getLastStudents();
}

