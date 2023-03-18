package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Course_dist")
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
    private Long group_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "course_id")
    private Long course_id;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Course_dist other = (Course_dist) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(group_id, other.group_id)
                && Objects.equals(course_id, other.course_id);
    }
}
