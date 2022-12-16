package nowak.kamil.sdjpajpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wp_usermeta")
@Getter
@Setter
public class UserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="umeta_id")
    private Long id;

//    private Long userId;
    @ManyToOne
    private User user;

    @Size(max = 255)
    @Column(columnDefinition = "longtext")
    private String metaKey;
    //@Lob // we told hibernate that it is a heavy object
    private String metaValue;
}
