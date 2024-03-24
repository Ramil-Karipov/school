package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {
    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyServiceImpl;

    @Test
    public void createFacultyTest() {
        Faculty faculty = new Faculty(1L, "asd", "red");
        facultyServiceImpl.createFaculty(faculty);
        Mockito.verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void findFacultyTest() {
        Faculty faculty = new Faculty(1L, "asd", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        Faculty actual = facultyServiceImpl.findFaculty(1L);
        Mockito.verify(facultyRepository, times(1)).findById(1L);

        assertEquals(faculty, actual);
    }

    @Test
    public void updateFacultyTest() {
        Faculty faculty = new Faculty(1L, "asd", "red");
        facultyServiceImpl.updateFaculty(faculty.getId(), faculty);
        Mockito.verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void deleteFacultyTest() {
        facultyServiceImpl.deleteFaculty(1L);
        Mockito.verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findByColorTest() {
        Faculty faculty = new Faculty(1L, "gr", "red");

        String color = "red";
        List<Faculty> expected = List.of(faculty);

        when(facultyRepository.findByColor(color)).thenReturn(expected);

        List<Faculty> actual = facultyRepository.findByColor(color);

        assertEquals(expected, actual);
    }
}
