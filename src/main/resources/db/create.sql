DROP TABLE IF EXISTS "Group" CASCADE;
CREATE TABLE "Group" (
	group_id serial PRIMARY KEY,
	number int NOT NULL,
	stream int NOT NULL,
	study_year int NOT NULL
);

DROP TABLE IF EXISTS "Student" CASCADE;
CREATE TABLE "Student" (
	student_id serial PRIMARY KEY,
	surname varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	patronymic varchar(255),
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT
);

DROP TABLE IF EXISTS "Lecturer" CASCADE;
CREATE TABLE "Lecturer" (
	lecturer_id serial PRIMARY KEY,
	surname varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    patronymic varchar(255)
);

DROP TABLE IF EXISTS "Audience" CASCADE;
CREATE TABLE "Audience" (
	audience_id serial PRIMARY KEY,
	number varchar(5) NOT NULL UNIQUE,
	capacity int DEFAULT 36 NOT NULL
);

DROP TABLE IF EXISTS "Course" CASCADE;
CREATE TABLE "Course" (
	course_id serial PRIMARY KEY,
	name varchar(255) NOT NULL UNIQUE,
	description text DEFAULT '' NOT NULL,
	coverage int NOT NULL,
	depth int DEFAULT 1 NOT NULL, -- Number of exercise per week
	study_year int
);

DROP TABLE IF EXISTS "Exercise" CASCADE;
CREATE TABLE "Exercise" (
	exercise_id serial PRIMARY KEY,
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
	lecturer_id int REFERENCES "Lecturer" (lecturer_id) ON DELETE RESTRICT
);

DROP TABLE IF EXISTS "Course_dist" CASCADE;
CREATE TABLE "Course_dist" (
    course_dist_id serial PRIMARY KEY,
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
    UNIQUE (group_id, course_id)
);

DROP TABLE IF EXISTS "Spec_course_dist" CASCADE;
CREATE TABLE "Spec_course_dist" (
    spec_course_dist_id serial PRIMARY KEY,
	student_id int REFERENCES "Student" (student_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
    UNIQUE (student_id, course_id)
);

DROP TABLE IF EXISTS "Lecturer_dist" CASCADE;
CREATE TABLE "Lecturer_dist" (
    lecturer_dist_id serial PRIMARY KEY,
	lecturer_id int REFERENCES "Lecturer" (lecturer_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
    UNIQUE (lecturer_id, course_id)
);

DROP TABLE IF EXISTS "Audience_dist" CASCADE;
CREATE TABLE "Audience_dist" (
    audience_dist_id serial PRIMARY KEY,
	audience_id int REFERENCES "Audience" (audience_id) ON DELETE RESTRICT,
	exercise_id int REFERENCES "Exercise" (exercise_id) ON DELETE RESTRICT,
    time int, -- Number of lesson
    UNIQUE (audience_id, time)
);

