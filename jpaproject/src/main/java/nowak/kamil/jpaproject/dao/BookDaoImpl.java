package nowak.kamil.jpaproject.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import nowak.kamil.jpaproject.domain.Book;
import nowak.kamil.jpaproject.repositories.BookRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }
}
