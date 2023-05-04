-- db filling example

\connect schedule_db

INSERT INTO "Group" (year, stream, number) VALUES
	(1, 1, 101),
	(2, 1, 201),
	(3, 1, 301),
	(4, 1, 401),
	(1, 2, 111),
    (1, 1, 102);

INSERT INTO "Student" (surname, name, patronymic, group_id) VALUES
	('Первов', 'Петр', 'Петрович', 1),
	('Перваков', 'Павел', 'Павлович', 1),
	('Второв', 'Василий', 'Васильевич', 2),
	('Вторкин', 'Виктор', 'Викторович', 2),
	('Трифонов', 'Трифон', 'Трифонович', 3),
	('Четвертак', 'Чибис', 'Чибисович', 4),
	('Аааев', 'Ааай', 'Аааевич', 5),
    ('Багов', 'Борис', 'Бебрович', 6);

INSERT INTO "Lecturer" (surname, name, patronymic) VALUES
	('Матанов', 'Михаил', 'Маркович'),
	('Линейный', 'Леонид', 'Леонтьевич'),
	('Праков', 'Прак', 'Пракович'),
	('Компилов', 'Константин', 'Кристианович');

INSERT INTO "Audience" (number, capacity) VALUES
	('П1', 200),
	('П2', 200),
    ('526', DEFAULT),
    ('526-б', DEFAULT),
    ('666', DEFAULT);

INSERT INTO "Course" (name, coverage, depth, year) VALUES
	('Линейная алгебра', 0, 2, 1),
	('Математический анализ I', 0, 2, 1),
	('Математический анализ II', 0, 1, 2),
	('Комплексный анализ', 0, 1, 2),
	('Функциональный анализ', 0, 1, 3),
	('Функциональное программирование', 1, 2, 4),
	('Комиляторные технологии', 2, 1, 1);

INSERT INTO "LecturerDist" (lecturer_id, course_id) VALUES
    (1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(2, 1),
    (2, 2),
	(3, 6),
	(4, 7);

INSERT INTO "Exercise" (exercise_id, course_id) VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 2);

INSERT INTO "GroupSchedule" (group_id, exercise_id, time) VALUES
    (1, 1, 0),
    (6, 1, 0),
    (1, 2, 1),
    (6, 2, 1),
    (5, 3, 0),
    (5, 4, 1),
    (1, 5, 2),
    (6, 5, 2),
    (1, 6, 3),
    (6, 6, 3),
    (5, 7, 2),
    (5, 8, 3);

INSERT INTO "LecturerSchedule" (lecturer_id, exercise_id, time) VALUES
    (1, 1, 0),
    (1, 2, 1),
    (2, 3, 0),
    (2, 4, 1),
    (1, 7, 2),
    (1, 8, 3),
    (2, 5, 2),
    (2, 6, 3);

INSERT INTO "AudienceSchedule" (audience_id, exercise_id, time) VALUES
    (1, 1, 0),
    (1, 2, 1),
    (2, 3, 0),
    (2, 4, 1),
    (1, 7, 2),
    (1, 8, 3),
    (2, 5, 2),
    (2, 6, 3);