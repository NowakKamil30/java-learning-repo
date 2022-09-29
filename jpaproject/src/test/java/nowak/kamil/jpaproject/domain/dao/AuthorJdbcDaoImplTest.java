package nowak.kamil.jpaproject.domain.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ComponentScan(basePackages = {"nowak.kamil.jpaproject.domain.dao"})
@Import(AuthorJdbcDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorJdbcDaoImplTest {

    @Autowired
    AuthorJdbcDao authorJdbcDao;

    @Test
    void getById() {

        final var author = authorJdbcDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void getByFirstName() {
        final var author = authorJdbcDao.getByFirstName("Kamil");

        assertThat(author).isNotNull();
    }
}