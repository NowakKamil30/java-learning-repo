package nowak.kamil.jpaproject.domain.dao;

import lombok.AllArgsConstructor;
import nowak.kamil.jpaproject.domain.AuthorJDBC;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class AuthorJdbcDaoImpl implements AuthorJdbcDao {

    private final DataSource dataSource;

    @Override
    public AuthorJDBC getById(Long id) {
        try (final var connection = dataSource.getConnection();
        final var statement = connection.createStatement();
        final var preparedStatment = connection.prepareStatement("SELECT * FROM author_jdbc where id = ?")) {
            preparedStatment.setLong(1, id);
            try (final var resultSet = preparedStatment.executeQuery()) {
                if (resultSet.next()) {

                    return AuthorJDBC.builder()
                            .id(id)
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthorJDBC getByFirstName(final String firstName) {
        try(final var connection = dataSource.getConnection();
        final var preparedStatment = connection.prepareStatement("""
        SELECT *
        FROM author_jdbc
        WHERE first_name = ?
        """)) {
            preparedStatment.setString(1, firstName);
            try (final var resultSet = preparedStatment.executeQuery()) {
                if (resultSet.next()) {
                    return AuthorJDBC.builder()
                            .id(resultSet.getLong("id"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .build();
                }
            }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthorJDBC save(AuthorJDBC authorJDBC) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
