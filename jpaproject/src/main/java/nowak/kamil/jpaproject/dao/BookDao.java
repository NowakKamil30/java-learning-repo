package nowak.kamil.jpaproject.dao;

import nowak.kamil.jpaproject.domain.Book;

public interface BookDao {
    Book findById(Long id);

    Book findByTitle(String title);
}
