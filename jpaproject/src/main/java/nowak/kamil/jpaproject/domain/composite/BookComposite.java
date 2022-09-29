package nowak.kamil.jpaproject.domain.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Entity
@IdClass(NameId.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookComposite {
    @Id
    private String title;
    @Id
    private String publisher;
    private String isbn;
    private Long author;
}
