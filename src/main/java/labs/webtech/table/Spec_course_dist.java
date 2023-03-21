package labs.webtech.table;

import labs.webtech.CompositeId.Spec_course_distId;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Spec_course_dist\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Spec_course_distId.class)
public class Spec_course_dist {

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
        Spec_course_dist other = (Spec_course_dist) _other;
        return Objects.equals(student, other.student)
                && Objects.equals(course, other.course);
    }
}
