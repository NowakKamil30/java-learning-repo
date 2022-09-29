package nowak.kamil.jpaproject.domain.jdbc.template;


import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AuthorTemplateDao {
    List<AuthorTemplate> findAllAuthorsByName(Pageable pageable);
    List<AuthorTemplate> findAllAuthors(Pageable pageable);
    List<AuthorTemplate> findAllAuthors(int pageSize, int offset);
    List<AuthorTemplate> findAllAuthors();
    AuthorTemplate getById(final Long id);
    AuthorTemplate findByName(final String name);
    AuthorTemplate save(final AuthorTemplate authorTemplate);
}
