package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Lecturer_dist")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Lecturer_dist implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "lecturer_dist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "lecturer_id")
    private Long lecturer_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "course_id")
    private Long course_id;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Lecturer_dist other = (Lecturer_dist) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(lecturer_id, other.lecturer_id)
                && Objects.equals(course_id, other.course_id);
    }
}
