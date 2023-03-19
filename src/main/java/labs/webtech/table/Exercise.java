package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Exercise")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Exercise implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "exercise_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "group_id")
    private Group group_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "course_id")
    private Course course_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "lecturer_id")
    private Lecturer lecturer_id;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Exercise other = (Exercise) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(group_id, other.group_id)
                && Objects.equals(course_id, other.course_id)
                && Objects.equals(lecturer_id, other.lecturer_id);
    }
}
