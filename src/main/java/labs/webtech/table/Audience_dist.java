package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Audience_dist",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"audience_id", "time"})
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Audience_dist implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "audience_dist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "audience_id")
    private Audience audience;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false, name = "time")
    @NonNull
    private Integer time;


    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Audience_dist other = (Audience_dist) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(audience, other.audience)
                && Objects.equals(exercise, other.exercise)
                && Objects.equals(time, other.time);
    }
}
