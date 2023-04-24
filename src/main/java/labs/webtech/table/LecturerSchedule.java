package labs.webtech.table;

import labs.webtech.compositeId.LecturerScheduleId;

import lombok.*;
import org.hibernate.annotations.Check;
import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "\"LecturerSchedule\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lecturer_id", "time"})
})
@Check(constraints = "time < 30")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LecturerScheduleId.class)
public class LecturerSchedule {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "lecturer_id")
    @NonNull
    private Lecturer lecturer;

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
        LecturerSchedule other = (LecturerSchedule) _other;
        return Objects.equals(lecturer, other.lecturer)
                && Objects.equals(exercise, other.exercise)
                && Objects.equals(time, other.time);
    }
}
