package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("addFaculty method invoked");
        facultyRepository.save(faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long facultyId) {
        logger.info("findFaculty method invoked");
        return facultyRepository.findById(facultyId).get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("editFaculty method invoked");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long facultyId) {
        logger.info("deleteFaculty method invoked");
        facultyRepository.deleteById(facultyId);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("getAllFaculties method invoked");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllFacultiesByColorOrName(String color, String name) {
        logger.info("getAllFacultiesByColorOrName method invoked");
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> getAllStudentsOfFaculty(long facultyId) {
        logger.info("getAllStudentsOfFaculty method invoked");
        return studentRepository.findAllByFaculty_Id(facultyId);
    }

    @Override
    public String getFacultyLongestName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }
}
