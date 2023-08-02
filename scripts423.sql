SELECT student.name, student.age, faculty.name
FROM student
         inner join faculty ON student.faculty_id = faculty.id

SELECT student.name
FROM student
         inner join avatar ON student.avatar_id = avatar.id