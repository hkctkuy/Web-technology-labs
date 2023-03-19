package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Course")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Course implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "course_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "description")
    @NonNull
    private String description = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "coverage_id")
    private Coverage coverage_id;

    @Column(nullable = false, name = "depth")
    @NonNull
    private Integer depth = 1;

    @Column(nullable = false, name = "study_year")
    @NonNull
    private Integer study_year;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Course other = (Course) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(description, other.description)
                && Objects.equals(coverage_id, other.coverage_id)
                && Objects.equals(depth, other.depth)
                && Objects.equals(study_year, other.study_year);
    }
}
