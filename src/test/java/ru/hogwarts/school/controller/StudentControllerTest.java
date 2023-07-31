package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testGetStudentInfo() {
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:"+port+"/student/{id}", Student.class, 53L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(53L, response.getBody().getId());
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setAge(30);
        student.setName("John");
        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:"+port+"/student", student, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getName());
        assertEquals(30, response.getBody().getAge());

    }

    @Test
    public void testEditStudent() {
        Student student = new Student();
        student.setAge(25);
        student.setName("John");
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:"+port+"/student", HttpMethod.PUT, new HttpEntity<>(student), Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getName());
        assertEquals(25, response.getBody().getAge());
    }

    @Test
    public void testDeleteStudent() {
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:"+port+"/student/{id}", HttpMethod.DELETE, null, Void.class, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllStudentsInfo() {
        ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:"+port+"/student", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetAllStudentsByAgeInfo() {
        ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:"+port+"/student/getByAge/{age}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {}, 25);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testFindByAgeBetween() {
        ResponseEntity<List<Student>> response = restTemplate.exchange("/student/findByAgeBetween?min={min}&max={max}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {}, 20, 30);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetFacultyOfStudentById() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/getFacultyOfStudentById/{studentId}", Faculty.class, 53L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}