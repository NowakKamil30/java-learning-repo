package nowak.kamil.jpaproject.repositories;


import nowak.kamil.jpaproject.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book jpaNamed(@Param("title") String title); //uzywa namedQuedy z klasy Book

    @Query("SELECT b FROM Book b WHERE b.title = ?1") //domyslnie uzywa HQL
    Book findBookByTitleUsingQuery(String title);

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Book findBookByTitleUsingQueryNamed(@Param("title") String title);

    @Query(value = "SELECT * FROM book WHERE title = :title", nativeQuery = true) //native query uzwa SQL i jest zalezny od uzytej bazy danych
    Book findBookByTitleUsingNativeQuery(@Param("title") String title);

    @Nullable //pozwalamy na nulla mimo ustawien z package-info
    Book findBookByTitle(@Nullable String title); // @Nullable String title pozwala uzyc nulla jako title
    // nie pozwoli ani uzyc nulla jako tytle ani zwrocic nulla zostanie rzucony exception
    Book readBookByTitle(String title);

    Stream<Book> findAllByTitleNotNull(); // znajdzie wszystkie book gdzie title != null

    @Async //potrzebne do pracy asynchronicznie z zapytaniami/ przydaje sie kiedy musimy pobrac kilka rzeczy z baz(y) równoczesnie
    Future<Book> queryBookByTitle(String title);

    Page<Book> findAllByTitleAndAndIsbnNotNull(final String title, final Pageable pageable);
}
