package labs.webtech.compositeId;

import labs.webtech.table.Course;
import labs.webtech.table.Group;

import java.io.Serializable;
import java.util.Objects;

public class CourseDistId implements Serializable {
    
    private Course course;
    
    private Group group;

    public CourseDistId() {}

    public CourseDistId(Course _course, Group _group) {
        course = _course;
        group = _group;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        CourseDistId other = (CourseDistId) _other;
        return Objects.equals(course, other.course)
                && Objects.equals(group, other.group);
    }

    @Override
    public int hashCode() {
        return course.getId().hashCode() * 1000 + group.getId().hashCode();
    }
}
