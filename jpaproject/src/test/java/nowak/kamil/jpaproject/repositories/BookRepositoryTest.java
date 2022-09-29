package nowak.kamil.jpaproject.repositories;

import nowak.kamil.jpaproject.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    final String title = "1342323";

    @BeforeEach
    void init() {
        bookRepository.save(Book.builder()
                .title(title)
                .isbn("232edfas")
                .build());
    }


    @Test
    void findAllBookByPageable() {
        final var pageable = PageRequest.of(0, 10);
        final var page = bookRepository.findAll(pageable);

        assertFalse(page.getContent().isEmpty());
    }

    @Test
    void findAllBookByPageableWithSorting() {
        final var pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"), Sort.Order.desc("publisher")));
        final var page = bookRepository.findAll(pageable);

        assertFalse(page.getContent().isEmpty());
    }

    @Test
    void findAllBookByTitle() {
        final var pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"), Sort.Order.desc("publisher")));
        final var page = bookRepository.findAllByTitleAndAndIsbnNotNull(title, pageable);

        assertFalse(page.getContent().isEmpty());
    }

    @Test
    void findAllBookByTitleWrong() {
        final var pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"), Sort.Order.desc("publisher")));
        final var page = bookRepository.findAllByTitleAndAndIsbnNotNull(title + "1", pageable);

        assertTrue(page.getContent().isEmpty());
    }

    @Test
    void findAllBookByTitleIsbnNull() {
        bookRepository.save(Book.builder()
                .title(title + "1")
                .build());
        final var pageable = PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("title"), Sort.Order.desc("publisher")));
        final var page = bookRepository.findAllByTitleAndAndIsbnNotNull(title + "1", pageable);

        assertTrue(page.getContent().isEmpty());
    }


}