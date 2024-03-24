package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentServiceImpl  implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    @Override
    public Student createStudent(Student student) {
        student.setId(lastId++);
        students.put(lastId, student);
        return student;
    }

    @Override
    public Student findStudent(long id) {
        return students.get(id);
    }

    @Override
    public Student updateStudent(long id, Student student) {
        if (!students.containsKey(id)) {
            return null;
        }
        students.put(id, student);
        return student;
    }

    @Override
    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        List<Student> sortedStudents = new ArrayList<>();
        students.forEach((id, student) -> {
            if (student.getAge() == age) {
                sortedStudents.add(student);
            }
        });
        return sortedStudents;
    }
}
