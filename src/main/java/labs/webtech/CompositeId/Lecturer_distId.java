package labs.webtech.CompositeId;

import labs.webtech.table.Course;
import labs.webtech.table.Lecturer;

import java.io.Serializable;
import java.util.Objects;

public class Lecturer_distId implements Serializable {

    private Course course;

    private Lecturer lecturer;

    public Lecturer_distId() {}

    public Lecturer_distId(Course _course, Lecturer _lecturer) {
        course = _course;
        lecturer = _lecturer;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Lecturer_distId other = (Lecturer_distId) _other;
        return Objects.equals(course, other.course)
                && Objects.equals(lecturer, other.lecturer);
    }

    @Override
    public int hashCode() {
        return course.getId().hashCode() * 1000 + lecturer.getId().hashCode();
    }
}