package labs.webtech.compositeId;

import labs.webtech.table.Course;
import labs.webtech.table.Lecturer;

import java.io.Serializable;
import java.util.Objects;

public class LecturerDistId implements Serializable {

    private Course course;

    private Lecturer lecturer;

    public LecturerDistId() {}

    public LecturerDistId(Course _course, Lecturer _lecturer) {
        course = _course;
        lecturer = _lecturer;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        LecturerDistId other = (LecturerDistId) _other;
        return Objects.equals(course, other.course)
                && Objects.equals(lecturer, other.lecturer);
    }

    @Override
    public int hashCode() {
        return course.getId().hashCode() * 1000 + lecturer.getId().hashCode();
    }
}
