package nowak.kamil.sdjpajpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;

@Entity
@Table(name = "wp_users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "user_login", length = 60) //!!! length is used only when hibernate generates schema on its own
    private String login;

    @NotNull
    @Size(max = 255)
    @Column(name = "user_pass", length = 255)
    private String password;

    @NotNull
    @Size(max = 50)
    @Column(name = "user_nicename", length = 50)
    private String nicename;

    @NotNull
    @Size(max = 100)
    @Email
    @Column(name = "user_email", length = 100)
    private String email;

    @NotNull
    @Size(max = 100)
    @URL
    @Column(name = "user_url", length = 100)
    private String url;

    @NotNull
    @Column(name = "user_registered")
    private Timestamp registered;

    @NotNull
    @Size(max = 255)
    @Column(name = "user_activation_key", length = 255) //length default value is 255
    private String activationKey;

    @NotNull
    @Column(name = "user_status")
    private Integer status;

    @NotNull
    @Size(max = 60)
    @Column(name = "display_name")
    @Basic(optional = false) // not null
    private String displayName;
}
