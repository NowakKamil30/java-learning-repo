package nowak.kamil.jpaproject.domain.authorcomposite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AuthorComposite {

    @EmbeddedId
    private AuthorId authorId;
    private String test;
}
