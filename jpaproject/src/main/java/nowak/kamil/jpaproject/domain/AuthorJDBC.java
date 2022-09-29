package nowak.kamil.jpaproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthorJDBC {
    private Long id;
    private String firstName;
    private String lastName;
}
