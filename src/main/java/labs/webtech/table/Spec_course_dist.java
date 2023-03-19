package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Spec_course_dist",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Spec_course_dist implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "spec_course_dist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "course_id")
    private Course course;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Spec_course_dist other = (Spec_course_dist) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(student, other.student)
                && Objects.equals(course, other.course);
    }
}
