package labs.webtech.table;

import labs.webtech.CompositeId.LecturerDistId;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"LecturerDist\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lecturer_id", "course_id"})
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LecturerDistId.class)
public class LecturerDist {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "lecturer_id")
    @NonNull
    private Lecturer lecturer;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "course_id")
    @NonNull
    private Course course;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        LecturerDist other = (LecturerDist) _other;
        return Objects.equals(lecturer, other.lecturer)
                && Objects.equals(course, other.course);
    }
}
