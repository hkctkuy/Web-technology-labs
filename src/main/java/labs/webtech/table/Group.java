package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Group\"")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Group implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "group_id")
    private Long id;

    @Column(nullable = false, name = "number")
    @NonNull
    private Integer number;

    @Column(nullable = false, name = "stream")
    @NonNull
    private Integer stream;

    @Column(nullable = false, name = "year")
    @NonNull
    private Integer year;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Group other = (Group) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(number, other.number)
                && Objects.equals(stream, other.stream)
                && Objects.equals(year, other.year);
    }
}
