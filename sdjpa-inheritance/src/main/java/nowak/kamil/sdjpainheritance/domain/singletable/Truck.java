package nowak.kamil.sdjpainheritance.domain.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("TRUCK_TYPE") // optional
public class Truck extends Vehicle {
    private String code;
}
