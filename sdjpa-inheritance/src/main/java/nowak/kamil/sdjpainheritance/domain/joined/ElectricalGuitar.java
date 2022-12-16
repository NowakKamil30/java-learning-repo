package nowak.kamil.sdjpainheritance.domain.joined;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ElectricalGuitar extends Guitar {
    private Integer numberOfPickups;
}
