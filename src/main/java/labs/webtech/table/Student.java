package labs.webtech.table;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Student")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Student implements TableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "student_id")
    private Long id;

    @Column(nullable = false, name = "surname")
    @NonNull
    private String surname;
    
    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "patronymic")
    private String patronymic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "group_id")
    private Group group;

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Student other = (Student) _other;
        return Objects.equals(id, other.id)
                && Objects.equals(surname, other.surname)
                && Objects.equals(name, other.name)
                && Objects.equals(patronymic, other.patronymic)
                && Objects.equals(group, other.group);
    }
}
