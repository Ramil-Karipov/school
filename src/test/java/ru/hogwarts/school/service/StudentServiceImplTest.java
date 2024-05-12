package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Test
    public void createStudentTest() {
        Student student = new Student(1L, "Harry", 11);
        studentServiceImpl.createStudent(student);
        Mockito.verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void findStudent() {
        Student student = new Student(1L, "Harry", 11);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student actual = studentServiceImpl.findStudent(1L);
        Mockito.verify(studentRepository, times(1)).findById(1L);

        assertEquals(student, actual);
    }

    @Test
    public void updateStudentTest() {
        Student student = new Student(1L, "Harry", 11);
        studentServiceImpl.updateStudent(student.getId(), student);
        Mockito.verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void deleteStudentTest() {
        studentServiceImpl.deleteStudent(1L);
        Mockito.verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findByAgeTest() {
        Student student = new Student(1L, "Harry", 11);

        int age = 11;
        List<Student> expected = List.of(student);

        when(studentRepository.findByAge(age)).thenReturn(expected);

        List<Student> actual = studentRepository.findByAge(age);

        assertEquals(expected, actual);
    }
}
