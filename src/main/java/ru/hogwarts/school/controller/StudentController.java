package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo (@PathVariable long id){
        Student student = studentService.findStudent(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public Student createStudent (@RequestBody Student student){
        return studentService.addStudent(student);
    }
    @PutMapping
    public ResponseEntity<Student> editStudent (@RequestBody Student student){
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent (@PathVariable long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public Collection<Student> getAllStudentsInfo (){
        return studentService.getAllStudents();
    }
    @GetMapping("/getByAge/{age}")
    public Collection<Student> getAllStudentsByAgeInfo (@PathVariable int age){
        return studentService.getAllStudentsByAge(age);
    }
    @GetMapping("/findByAgeBetween")
    Collection<Student> findByAgeBetween (@RequestParam int min, @RequestParam int max){
        return studentService.findByAgeBetween(min, max);
    }
    @GetMapping("/getFacultyOfStudentById/{studentId}")
    public ResponseEntity<Faculty> getFacultyOfStudentById(@PathVariable long studentId){
        return ResponseEntity.ok(studentService.getFacultyOfStudentById(studentId));
    }
    @GetMapping("/get-Count-All-Students")
    public ResponseEntity<Integer> getCountAllStudents(){
        return ResponseEntity.ok(studentService.getCountAllStudents());
    }
    @GetMapping("/get-Avg-Age-Students")
    public ResponseEntity<Double> getAvgAgeStudents(){
        return ResponseEntity.ok(studentService.getAverageAgeStudents());
    }
    @GetMapping("/get-Last-Five-Students")
    public ResponseEntity<List<Student>> getLastFiveStudents(){
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @GetMapping("/start-with-a")
    public ResponseEntity<List<String>> getStudentsStartWithA (){
        return ResponseEntity.ok(studentService.getStudentsStartWithA());
    }
    @GetMapping("/get-average-age-students-2")
    public ResponseEntity<Double> getAverageAgeStudents2 (){
        return ResponseEntity.ok(studentService.getAverageAgeStudents2());
    }
}
