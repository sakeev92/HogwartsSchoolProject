package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;
import java.util.List;
@Repository
public interface  StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int minAge,int maxAge);
    List<Student> findAllByFaculty_Id (Long facultyId);
    List<Student> findAllByAge(int age);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountAllStudents();
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getAvgAgeStudents();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

}
