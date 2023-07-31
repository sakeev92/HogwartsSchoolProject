-- liquibase formatted sql
-- changeset agurenko:1
CREATE INDEX student_name_index ON student (name)

-- liquibase formatted sql
-- changeset agurenko:2
CREATE INDEX faculty_nc_idx ON faculty (name, color);
