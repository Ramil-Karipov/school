package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.FormatterClosedException;

@Service
public class FacultyServiceImpl {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(FormatterClosedException::new);
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        Faculty existingFaculty = findFaculty(id);
        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());
        facultyRepository.save(existingFaculty);
        return existingFaculty;

    }

    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }


    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);

    }


    public Collection<Faculty> getFacultiesByColor(String color) {

        return facultyRepository.findByColor(color);
    }
}
