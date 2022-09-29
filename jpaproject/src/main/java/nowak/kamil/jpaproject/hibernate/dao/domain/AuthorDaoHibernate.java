package nowak.kamil.jpaproject.hibernate.dao.domain;

import jakarta.persistence.*;

@NamedQueries(value = {
        @NamedQuery(name = "author_find_all", query = "SELECT a FROM AuthorDaoHibernate a"),
        @NamedQuery(name = "find_by_name", query = "SELECT a FROM AuthorDaoHibernate a " +
                "WHERE a.firstName = :first_name and a.lastName = :last_name")
})
@Entity
public class AuthorDaoHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
