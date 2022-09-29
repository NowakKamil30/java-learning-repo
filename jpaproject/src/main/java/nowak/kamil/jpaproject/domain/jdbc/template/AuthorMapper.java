package nowak.kamil.jpaproject.domain.jdbc.template;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<AuthorTemplate> {
    @Override
    public AuthorTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AuthorTemplate.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }
}
