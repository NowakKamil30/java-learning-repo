package nowak.kamil.jpaproject.domain.authorcomposite;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class AuthorId implements Serializable {
    private String firstName;
    private String lastName;
}
