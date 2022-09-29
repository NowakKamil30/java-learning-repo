package nowak.kamil.jpaproject.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nowak.kamil.jpaproject.domain.AuthorUuid;
import nowak.kamil.jpaproject.domain.Book;
import nowak.kamil.jpaproject.domain.BookNatural;
import nowak.kamil.jpaproject.domain.BookUuid;
import nowak.kamil.jpaproject.domain.authorcomposite.AuthorComposite;
import nowak.kamil.jpaproject.domain.authorcomposite.AuthorId;
import nowak.kamil.jpaproject.domain.composite.BookComposite;
import nowak.kamil.jpaproject.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
@Log4j2
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;
    private final BookNaturalRepository bookNaturalRepository;
    private final BookCompositeRepository compositeRepository;
    private final AuthorCompositeRepository authorCompositeRepository;

    @Override
    public void run(String... args) throws Exception {
        log.debug("run - start prepare data");
        bookRepository.deleteAll();
        authorUuidRepository.deleteAll();

        bookRepository.save(Book.builder()
                        .publisher("h2")
                        .isbn("2")
                        .title("213321")
                .build());

        final var savedAuthor = authorUuidRepository.save(AuthorUuid.builder()
                        .firstName("test")
                        .lastName("lastTest")
                .build());

        log.info(savedAuthor);

        final var savedBook = bookUuidRepository.save(BookUuid.builder()
                        .isbn("423423")
                        .publisher("pub")
                        .title("title")
                .build());

        log.info(savedBook);

        final var savedBookNatural = bookNaturalRepository.save(BookNatural.builder().title("lala").build());

        log.info(savedBookNatural);

        final var savedBookComposite = compositeRepository.save(BookComposite.builder()
                .title("asdsadsa").publisher("sadsadas").build());

        log.info(savedBookComposite);

        final var savedAuthorComposite = authorCompositeRepository.save(AuthorComposite.builder()
                .authorId(AuthorId.builder().firstName("a").lastName("b").build()).build());

        log.info(savedAuthorComposite);
    }
}
