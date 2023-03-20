-- db filling example

\connect schedule_db

INSERT INTO "Group" (study_year, stream, number) VALUES
	(1, 1, 101),
	(2, 1, 201),
	(3, 1, 301),
	(4, 1, 401),
	(1, 2, 111);

INSERT INTO "Student" (surname, name, patronymic, group_id) VALUES
	('Первов', 'Петр', 'Петрович', 1),
	('Перваков', 'Павел', 'Павлович', 1),
	('Второв', 'Василий', 'Васильевич', 2),
	('Вторкин', 'Виктор', 'Викторович', 2),
	('Трифонов', 'Трифон', 'Трифонович', 3),
	('Четвертак', 'Чибис', 'Чибисович', 4),
	('Аааев', 'Ааай', 'Аааевич', 5);

INSERT INTO "Lecturer" (surname, name, patronymic) VALUES
	('Матанов', 'Михаил', 'Маркович'),
	('Линейный', 'Леонид', 'Леонтьевич'),
	('Праков', 'Прак', 'Пракович'),
	('Компилов', 'Константин', 'Кристианович');

INSERT INTO "Audience" (number, capacity) VALUES
	('526', DEFAULT),
	('526-б', DEFAULT),
	('666', DEFAULT),
	('П1', 200);

INSERT INTO "Coverage" (type) VALUES
	('Поток'),
	('Группа'),
	('Спец. курс');

INSERT INTO "Course" (name, coverage_id, depth, study_year) VALUES
	('Линейная алгебра', 1, 2, 1),
	('Математический анализ I', 1, 2, 1),
	('Математический анализ II', 1, 1, 2),
	('Комплексный анализ', 1, 1, 2),
	('Функциональный анализ', 1, 1, 3),
	('Функциональное программирование', 2, 2, 4),
	('Комиляторные технологии', 3, 1, NULL);

INSERT INTO "Lecturer_dist" (lecturer_id, course_id) VALUES
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(2, 1),
	(3, 6),
	(4, 7);
