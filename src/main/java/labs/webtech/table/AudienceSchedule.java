package labs.webtech.table;

import labs.webtech.compositeId.AudienceScheduleId;

import lombok.*;
import org.hibernate.annotations.Check;
import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "\"AudienceSchedule\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"audience_id", "time"})
})
@Check(constraints = "time is null")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AudienceScheduleId.class)
public class AudienceSchedule {

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
        AudienceSchedule other = (AudienceSchedule) _other;
        return Objects.equals(audience, other.audience)
                && Objects.equals(exercise, other.exercise)
                && Objects.equals(time, other.time);
    }
}
