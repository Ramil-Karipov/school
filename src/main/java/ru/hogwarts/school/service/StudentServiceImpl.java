package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.FormatterClosedException;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
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


    public Student deleteStudent(long id) {
        studentRepository.deleteById(id);
        return null;
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }


    public Collection<Student> getStudentsByAge(int age) {

        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getAllByAgeBetween(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

}

