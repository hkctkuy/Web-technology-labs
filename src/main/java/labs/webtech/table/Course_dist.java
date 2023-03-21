package labs.webtech.table;

import labs.webtech.CompositeId.Course_distId;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Course_dist\"",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_id", "course_id"})
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Course_distId.class)
public class Course_dist {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "group_id")
    @NonNull
    private Group group;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "course_id")
    @NonNull
    private Course course;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Course_dist other = (Course_dist) _other;
        return Objects.equals(group, other.group)
                && Objects.equals(course, other.course);
    }
}
