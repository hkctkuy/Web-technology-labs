package labs.webtech.CompositeId;

import labs.webtech.table.Course;
import labs.webtech.table.Student;

import java.io.Serializable;
import java.util.Objects;

public class Spec_course_distId implements Serializable {

    private Course course;

    private Student student;

    public Spec_course_distId() {}

    public Spec_course_distId(Course _course, Student _student) {
        course = _course;
        student = _student;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Spec_course_distId other = (Spec_course_distId) _other;
        return Objects.equals(course, other.course)
                && Objects.equals(student, other.student);
    }

    @Override
    public int hashCode() {
        return course.getId().hashCode() * 1000 + student.getId().hashCode();
    }
}
