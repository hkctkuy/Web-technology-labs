package labs.webtech.table;

import labs.webtech.CompositeId.GroupScheduleId;

import lombok.*;
import org.hibernate.annotations.Check;
import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "\"GroupSchedule\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_id", "time"})
})
@Check(constraints = "time < 30")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GroupScheduleId.class)
public class GroupSchedule {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "group_id")
    @NonNull
    private Group group;

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
        GroupSchedule other = (GroupSchedule) _other;
        return Objects.equals(group, other.group)
                && Objects.equals(exercise, other.exercise)
                && Objects.equals(time, other.time);
    }
}
