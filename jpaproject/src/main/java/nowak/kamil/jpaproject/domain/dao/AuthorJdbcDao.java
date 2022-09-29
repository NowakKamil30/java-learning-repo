package nowak.kamil.jpaproject.domain.dao;

import nowak.kamil.jpaproject.domain.AuthorJDBC;

public interface AuthorJdbcDao {

    AuthorJDBC getById(Long id);
    AuthorJDBC getByFirstName(final String firstName);
    AuthorJDBC save(final AuthorJDBC authorJDBC);
    void deleteById(final Long id);
}
