package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "\"Lecturer\"")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Lecturer implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "lecturer_id")
    private Long id;

    @Column(nullable = false, name = "surname")
    @NonNull
    private String surname;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "patronymic")
    @NonNull
    private String patronymic;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Lecturer other = (Lecturer) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(surname, other.surname)
                && Objects.equals(name, other.name)
                && Objects.equals(patronymic, other.patronymic);
    }
}
