package labs.webtech.table;

import labs.webtech.CompositeId.Audience_distId;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Audience_dist\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"audience_id", "time"})
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Audience_distId.class)
public class Audience_dist {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "audience_id")
    @NonNull
    private Audience audience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "exercise_id")
    @NonNull
    private Exercise exercise;

    @Id
    @Column(nullable = false, name = "time")
    @NonNull
    private Integer time;


    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Audience_dist other = (Audience_dist) _other;
        return Objects.equals(audience, other.audience)
                && Objects.equals(exercise, other.exercise)
                && Objects.equals(time, other.time);
    }
}
