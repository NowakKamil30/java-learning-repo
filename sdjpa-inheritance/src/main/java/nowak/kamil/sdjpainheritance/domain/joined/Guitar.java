package nowak.kamil.sdjpainheritance.domain.joined;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Guitar extends Instrument {
    private String name;
}
