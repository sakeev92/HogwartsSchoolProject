
select * from student;

select * from student where age >=10 and age<20;

select name from student;

select * from student where name like '%а%';

select * from student where age<id;

select * from student order by age;

SELECT s. * FROM student AS s, faculty AS f WHERE s.faculty_id = f.id AND f.id = 4