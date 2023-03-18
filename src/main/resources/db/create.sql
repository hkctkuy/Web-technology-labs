CREATE TABLE "Group" (
	group_id serial PRIMARY KEY,
	number int NOT NULL,
	stream int NOT NULL,
	study_year int NOT NULL
);

CREATE TABLE Student (
	student_id serial PRIMARY KEY,
	surname varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	patronymic varchar(255),
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT
);

CREATE TABLE Lecturer (
	lecturer_id serial PRIMARY KEY,
	surname varchar(255) NOT NULL,
        name varchar(255) NOT NULL,
        patronymic varchar(255)
);

CREATE TABLE Audience (
	audience_id serial PRIMARY KEY,
	number varchar(5) NOT NULL UNIQUE,
	capacity int DEFAULT 36
);

CREATE TABLE Coverage (
	coverage_id serial PRIMARY KEY,
	type varchar(10)
);

CREATE TABLE Course (
	course_id serial PRIMARY KEY,
	name varchar(255) NOT NULL UNIQUE,
	description text DEFAULT '',
	coverage_id int REFERENCES Coverage (coverage_id) ON DELETE RESTRICT,
	depth int DEFAULT 1, -- Number of exercise per week
	study_year int	
);

CREATE TABLE Exercise (
	exercise_id serial PRIMARY KEY,
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT,
	course_id int REFERENCES Course (course_id) ON DELETE RESTRICT,
	lecturer_id int REFERENCES Lecturer (lecturer_id) ON DELETE RESTRICT
);

CREATE TABLE Course_dist (
	group_id int REFERENCES "Group" (group_id) ON DELETE RESTRICT,
	course_id int REFERENCES Course (course_id) ON DELETE RESTRICT,
	PRIMARY KEY (group_id, course_id)
);

CREATE TABLE Spec_course_dist (
	student_id int REFERENCES Student (student_id) ON DELETE RESTRICT,
	course_id int REFERENCES Course (course_id) ON DELETE RESTRICT,
	PRIMARY KEY (student_id, course_id)
);

CREATE TABLE Lecturer_dist (
	lecturer_id int REFERENCES Lecturer (lecturer_id) ON DELETE RESTRICT,
	course_id int REFERENCES Course (course_id) ON DELETE RESTRICT,
	PRIMARY KEY (lecturer_id, course_id)
);

CREATE TABLE Audience_dist (
	audience_id int REFERENCES Audience (audience_id) ON DELETE RESTRICT,
	time int, -- Number of lesson
	exercise_id int REFERENCES Exercise (exercise_id) ON DELETE RESTRICT,
	PRIMARY KEY (audience_id, time)
);

