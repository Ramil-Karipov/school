1. Получить всех студентов, возраст которых находится между 10 и 20 (можно подставить любые числа, главное, чтобы нижняя граница была меньше верхней).
2. Получить всех студентов, но отобразить только список их имен.
3. Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).
4. Получить всех студентов, у которых возраст меньше идентификатора.
5. Получить всех студентов упорядоченных по возрасту.

1. select *
   from student s
   where age between 10 and 20
2. select "name"
   from student s
3. select *
   from student s
   where name like '%O%';
4. select *
   from student s
   where age<id;
5. select *
   from student s
   order by age;


   SELECT student.name, student.age, faculty.name
   FROM student
   INNER JOIN faculty ON student.faculty_id= faculty_id;

   SELECT student
   FROM student
   INNER JOIN avatar a ON student.id= a.student_id;