package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty (Faculty faculty);
    Faculty findFaculty (long id);
    Faculty editFaculty (Faculty faculty);
    void deleteFaculty (long id);
    Collection<Faculty> getAllFaculties ();
    Collection<Faculty> getAllFacultiesByColorOrName(String color, String name);
    Collection<Student> getAllStudentsOfFaculty(long facultyId);
    String getFacultyLongestName ();
}

