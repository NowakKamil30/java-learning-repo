package nowak.kamil.jpaproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookNatural {

    @Id
    private String title;
    private String isbn;
    private String publisher;
    private Long author;
}
