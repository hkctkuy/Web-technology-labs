package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Course_dist",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_id", "course_id"})
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Course_dist implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "course_dist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "course_id")
    private Course course;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Course_dist other = (Course_dist) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(group, other.group)
                && Objects.equals(course, other.course);
    }
}
