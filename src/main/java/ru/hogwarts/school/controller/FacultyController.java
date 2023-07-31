package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo (@PathVariable long id){
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty != null) {
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public Faculty createFaculty (@RequestBody Faculty faculty){
        return facultyService.addFaculty(faculty);
    }
    @PutMapping
    public ResponseEntity<Faculty> editFaculty (@RequestBody Faculty faculty){
         Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty (@PathVariable long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public Collection<Faculty> getAllFacultiesInfo (){
        return facultyService.getAllFaculties();
    }

    @GetMapping("/getByColorOrName")
    public Collection<Faculty> getAllFacultiesByColorOrName (@RequestParam(required = false) String color,
                                                             @RequestParam(required = false) String name){
        return facultyService.getAllFacultiesByColorOrName(color, name);
    }
    @GetMapping("/getAllStudentsOfFaculty/{facultyId}")
    public Collection<Student> getAllStudentsOfFaculty(@PathVariable long facultyId){
        return facultyService.getAllStudentsOfFaculty(facultyId);
    }

    @GetMapping("/get-longest-name")
    public ResponseEntity<String> getFacultyLongestName (){
        return ResponseEntity.ok(facultyService.getFacultyLongestName());
    }
}
