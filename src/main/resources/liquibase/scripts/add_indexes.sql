-- liquibase formatted sql

-- changeset ramil:add_indexes
    create index student_name_ing on student (name);
    create index faculty_ing on faculty (name, color);

