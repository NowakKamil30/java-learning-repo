package nowak.kamil.jpaproject;


import nowak.kamil.jpaproject.domain.AuthorUuid;
import nowak.kamil.jpaproject.domain.BookUuid;
import nowak.kamil.jpaproject.repositories.AuthorUuidRepository;
import nowak.kamil.jpaproject.repositories.BookRepository;
import nowak.kamil.jpaproject.repositories.BookUuidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"nowak.kamil.jpaproject.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorUuidRepository authorUuidRepository;
    @Autowired
    BookUuidRepository bookUuidRepository;

    @Test
    void testMySQL() {
        final var countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(1);
    }

    @Test
    void saveBookUuidShouldAddNewObject() {
        final var couterBefore = bookUuidRepository.count();
        bookUuidRepository.save(BookUuid.builder().build());
        assertThat(bookUuidRepository.count()).isEqualTo(couterBefore + 1);
    }

    @Test
    void saveAuthorUuidShouldAddNewObject() {
        final var couterBefore = authorUuidRepository.count();
        authorUuidRepository.save(AuthorUuid.builder().build());
        assertThat(authorUuidRepository.count()).isEqualTo(couterBefore + 1);
    }

    @Test
    void findByIdAuthorUuidShoudlReturnCorrectValue() {
        final var savedAuthor = authorUuidRepository.save(AuthorUuid.builder().lastName("test").build());
        final var fondedAuthor = authorUuidRepository.findById(savedAuthor.getId());

        assertThat(fondedAuthor.get()).isNotNull();
        assertThat(fondedAuthor.get().getLastName()).isEqualTo(savedAuthor.getLastName());
    }
}
