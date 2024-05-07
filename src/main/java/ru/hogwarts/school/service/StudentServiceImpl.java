package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.FormatterClosedException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    public Student createStudent(Student student) {
        logger.info("Method create invoked");
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        logger.info("Method findStudent invoked");
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to get by id" + id);
                    return new FormatterClosedException();
                });

    }


    public Student updateStudent(long id, Student student) {
        logger.info("Method update invoked");
        Student exsistingStudent = findStudent(id);
        exsistingStudent.setAge(student.getAge());
        exsistingStudent.setName(student.getName());
        return studentRepository.save(student);
    }


    public Student deleteStudent(long id) {
        logger.info("Method delete invoked");
        studentRepository.deleteById(id);
        return null;
    }

    public Collection<Student> getAll() {
        logger.info("Method getAll invoked");
        return studentRepository.findAll();
    }


    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Method getStudentsByAge invoked");
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getAllByAgeBetween(int min, int max) {
        logger.info("Method getAllByAgeBetween invoked");
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public long count() {
        logger.info("Method count invoked");
        return studentRepository.countStudent();
    }

    public double getAverageAge() {
        logger.info("Method getAverageAge invoked");
        return studentRepository.findAverageAge();
    }

    public List<Student> getLastStudents() {
        logger.info("Method getLastStudents invoked");
        return studentRepository.findLastStudents(5);

    }
}

