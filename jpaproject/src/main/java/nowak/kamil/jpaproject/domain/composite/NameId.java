package nowak.kamil.jpaproject.domain.composite;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class NameId implements Serializable {
    private String title;
    private String publisher;
}
