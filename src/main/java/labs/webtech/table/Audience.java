package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Audience\"")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Audience implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "audience_id")
    private Long id;

    @Column(length = 5, nullable = false, unique = true, name = "number")
    @NonNull
    private String number;

    @Column(nullable = false, name = "capacity")
    @NonNull
    private Integer capacity;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Audience other = (Audience) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(number, other.number)
                && Objects.equals(capacity, other.capacity);
    }
}
