package nowak.kamil.jpaproject.domain.jdbc.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthorTemplate {
    private Long id;
    private String firstName;
    private String lastName;
}
