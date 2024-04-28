package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findAllByAgeBetween(int min, int max);

    @Query(value = "SELECT COUNT(*)FROM student", nativeQuery = true)
    long countStudent();

    @Query(value = "SELECT AVG(age)FROM student", nativeQuery = true)
    double findAverageAge();

    @Query(value = "SELECT*FROM student ORDER BY id DESC LIMIT :quantity", nativeQuery = true)
    List<Student> findLastStudents(@Param("quantity") int quantity);

}
