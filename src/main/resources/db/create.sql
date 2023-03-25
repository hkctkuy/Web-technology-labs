DROP TABLE IF EXISTS "Group" CASCADE;
CREATE TABLE "Group" (
	group_id serial PRIMARY KEY,
	number int NOT NULL,
	stream int NOT NULL,
	year int NOT NULL
);

DROP TABLE IF EXISTS "Student" CASCADE;
CREATE TABLE "Student" (
	student_id serial PRIMARY KEY,
	surname varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	patronymic varchar(255),
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT NOT NULL
);

DROP TABLE IF EXISTS "Lecturer" CASCADE;
CREATE TABLE "Lecturer" (
	lecturer_id serial PRIMARY KEY,
	surname varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    patronymic varchar(255) NOT NULL
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
	depth int NOT NULL, -- Number of exercise per week
	year int NOT NULL
);

DROP TABLE IF EXISTS "Exercise" CASCADE;
CREATE TABLE "Exercise" (
	exercise_id serial PRIMARY KEY,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT NOT NULL
);

DROP TABLE IF EXISTS "CourseDist" CASCADE;
CREATE TABLE "CourseDist" (
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
    PRIMARY KEY (group_id, course_id)
);

DROP TABLE IF EXISTS "SpecCourseDist" CASCADE;
CREATE TABLE "SpecCourseDist" (
	student_id int REFERENCES "Student" (student_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
    PRIMARY KEY (student_id, course_id)
);

DROP TABLE IF EXISTS "LecturerDist" CASCADE;
CREATE TABLE "LecturerDist" (
	lecturer_id int REFERENCES "Lecturer" (lecturer_id) ON DELETE RESTRICT,
	course_id int REFERENCES "Course" (course_id) ON DELETE RESTRICT,
    PRIMARY KEY (lecturer_id, course_id)
);

DROP TABLE IF EXISTS "AudienceSchedule" CASCADE;
CREATE TABLE "AudienceSchedule" (
	audience_id int REFERENCES "Audience" (audience_id) ON DELETE RESTRICT,
	exercise_id int REFERENCES "Exercise" (exercise_id) ON DELETE RESTRICT,
    time int CHECK (time < 30), -- Number of lesson
    PRIMARY KEY (audience_id, time)
);

DROP TABLE IF EXISTS "GroupSchedule" CASCADE;
CREATE TABLE "GroupSchedule" (
    group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT,
    exercise_id int REFERENCES "Exercise" (exercise_id) ON DELETE RESTRICT,
    time int CHECK (time < 30), -- Number of lesson
    PRIMARY KEY (group_id, time)
);

DROP TABLE IF EXISTS "LecturerSchedule" CASCADE;
CREATE TABLE "LecturerSchedule" (
    lecturer_id int REFERENCES "Lecturer" (lecturer_id) ON DELETE RESTRICT,
    exercise_id int REFERENCES "Exercise" (exercise_id) ON DELETE RESTRICT,
    time int CHECK (time < 30), -- Number of lesson
    PRIMARY KEY (lecturer_id, time)
);

