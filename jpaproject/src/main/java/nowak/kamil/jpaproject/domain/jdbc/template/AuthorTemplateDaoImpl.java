package nowak.kamil.jpaproject.domain.jdbc.template;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthorTemplateDaoImpl implements AuthorTemplateDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AuthorTemplate> findAllAuthorsByName(Pageable pageable) {
        return jdbcTemplate.query("""
                SELECT * FROM author_template 
                order by first_name """ + pageable.getSort().getOrderFor("first_name").getDirection().name() +
                " limit ? offset ?", getRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<AuthorTemplate> findAllAuthors(Pageable pageable) {
        return jdbcTemplate.query(
                "SELECT * FROM author_template LIMIT ? OFFSET ?", getRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<AuthorTemplate> findAllAuthors(int pageSize, int offset) {
        return jdbcTemplate.query(
                "SELECT * FROM author_template LIMIT ? OFFSET ?", getRowMapper(), pageSize, offset);
    }

    @Override
    public List<AuthorTemplate> findAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM author_template", getRowMapper());
    }

    @Override
    public AuthorTemplate getById(final Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author_template where id = ?", getRowMapper(), id);
    }

    @Override
    public AuthorTemplate findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM author_template where first_name = ?", getRowMapper(), name);

    }

    @Override
    public AuthorTemplate save(AuthorTemplate authorTemplate) {
        jdbcTemplate.update("INSERT INTO author_template (first_name, last_name) VALUES (?,?)", authorTemplate.getFirstName(), authorTemplate.getLastName());

        final var id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return getById(id);
    }

    private RowMapper<AuthorTemplate> getRowMapper() {
        return new AuthorMapper();
    }
}
