package nowak.kamil.jpaproject.domain;

import jakarta.persistence.*;
import lombok.*;

@NamedQuery(name = "Book.jpaNamed", query = "SELECT b FROM Book b WHERE b.title = :title")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    private Long author;
}
