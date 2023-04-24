package labs.webtech.table;

import labs.webtech.compositeId.SpecCourseDistId;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"SpecCourseDist\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SpecCourseDistId.class)
public class SpecCourseDist {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "student_id")
    @NonNull
    private Student student;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "course_id")
    @NonNull
    private Course course;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        SpecCourseDist other = (SpecCourseDist) _other;
        return Objects.equals(student, other.student)
                && Objects.equals(course, other.course);
    }
}
