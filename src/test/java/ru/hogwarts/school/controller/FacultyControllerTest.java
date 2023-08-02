package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private FacultyController facultyController;


    @Test
    public void testGetFacultyById() throws Exception {
        Long facultyId = 1L;
        String name = "Griffindor";
        String color = "red";
        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", facultyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Long id = 1L;
        String name = "Griffindor";
        String color = "red";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testEditFaculty() throws Exception {
        long facultyId = 1L;
        String facultyName = "Faculty 1";
        String color = "red";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", facultyId);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", color);

        given(facultyService.editFaculty(any(Faculty.class))).willReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        long facultyId = 1L;

        mockMvc.perform(delete("/faculty/{id}", facultyId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllFacultiesInfo() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Faculty 1");
        faculty1.setColor("red");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Faculty 2");
        faculty2.setColor("blue");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);
        given(facultyService.getAllFaculties()).willReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Faculty 1"))
                .andExpect(jsonPath("$[0].color").value("red"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Faculty 2"))
                .andExpect(jsonPath("$[1].color").value("blue"));
    }

    @Test
    public void testGetAllFacultiesByColorInfo() throws Exception {
        String color = "red";
        String name = "Faculty 2";
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Faculty 1");
        faculty1.setColor("red");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Faculty 2");
        faculty2.setColor("red");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);
        when(facultyService.getAllFacultiesByColor(color)).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/getByColor/{color}", color))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Faculty 1"))
                .andExpect(jsonPath("$[0].color").value("red"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Faculty 2"))
                .andExpect(jsonPath("$[1].color").value("red"));

    }

    @Test
    public void testGetAllFacultiesByColorOrName() throws Exception {
        String color = "red";
        String name = "Faculty 2";
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Faculty 1");
        faculty1.setColor("red");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Faculty 2");
        faculty2.setColor("blue");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);
        given(facultyService.getAllFacultiesByColorOrName(color, name)).willReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/getByColorOrName")
                        .param("color", color)
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Faculty 1"))
                .andExpect(jsonPath("$[0].color").value("red"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Faculty 2"))
                .andExpect(jsonPath("$[1].color").value("blue"));
    }
}