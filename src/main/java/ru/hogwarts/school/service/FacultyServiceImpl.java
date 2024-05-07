package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.FormatterClosedException;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {

        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        logger.info("Method create invoked");
        return facultyRepository.save(faculty);
    }


    public Faculty findFaculty(long id) {
        logger.info("Method indFaculty invoked");
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to get by id" + id);
                    return new FormatterClosedException();
                });
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        logger.info("Method update invoked");
        Faculty existingFaculty = findFaculty(id);
        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());
        facultyRepository.save(existingFaculty);
        return existingFaculty;

    }

    public Collection<Faculty> getAll() {
        logger.info("Method getAll invoked");
        return facultyRepository.findAll();
    }


    public Faculty deleteFaculty(long id) {
        logger.info("Method delete invoked");
        Faculty faculty = facultyRepository.findById(id).orElseThrow();
        facultyRepository.delete(faculty);
        return faculty;

    }


    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("Method getFacultiesByColor invoked");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getByNameOrColorIgnoreCase(String name, String color) {
        logger.info("Method getByNameOrColorIgnoreCase invoked");
        return facultyRepository.findByNameOrColorIgnoreCase(color, name);
    }
}
