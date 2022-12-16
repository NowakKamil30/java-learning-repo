package nowak.kamil.sdjpainheritance.domain.msuper;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class OrderHeader extends BaseEntity {

    private String name;
}
