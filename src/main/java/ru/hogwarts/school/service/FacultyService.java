package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import java.util.Collection;
public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    Collection<Faculty> getFacultiesByColor(String color);
}
