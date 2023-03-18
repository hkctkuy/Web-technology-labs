package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Coverage")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Coverage implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "coverage_id")
    private Long id;

    @Column(nullable = false, unique = true, name = "type")
    @NonNull
    private String type;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Coverage other = (Coverage) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(type, other.type);
    }
}
