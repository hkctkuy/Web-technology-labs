package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Exercise\"")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Exercise implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "exercise_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "group_id")
    @NonNull
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "course_id")
    @NonNull
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "lecturer_id")
    @NonNull
    private Lecturer lecturer;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Exercise other = (Exercise) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(group, other.group)
                && Objects.equals(course, other.course)
                && Objects.equals(lecturer, other.lecturer);
    }
}
