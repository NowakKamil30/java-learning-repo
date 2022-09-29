package nowak.kamil.jpaproject.dao;

import nowak.kamil.jpaproject.domain.Author;

public interface AuthorDao {

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteById(Long id);
}
