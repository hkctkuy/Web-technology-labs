package labs.webtech.CompositeId;

import labs.webtech.table.Course;
import labs.webtech.table.Group;

import java.io.Serializable;
import java.util.Objects;

public class Course_distId implements Serializable {
    
    private Course course;
    
    private Group group;

    public Course_distId() {}

    public Course_distId(Course _course, Group _group) {
        course = _course;
        group = _group;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Course_distId other = (Course_distId) _other;
        return Objects.equals(course, other.course)
                && Objects.equals(group, other.group);
    }

    @Override
    public int hashCode() {
        return course.getId().hashCode() * 1000 + group.getId().hashCode();
    }
}
