package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.FormatterClosedException;

@Service
public class StudentServiceImpl {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(FormatterClosedException::new);
    }


    public Student updateStudent(long id, Student student) {
        Student exsistingStudent = findStudent(id);
        exsistingStudent.setAge(student.getAge());
        exsistingStudent.setName(student.getName());
        return studentRepository.save(student);
    }


    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }


    public Collection<Student> getStudentsByAge(int age) {

        return studentRepository.findByAge(age);
    }
}
