package nowak.kamil.jpaproject.hibernate.dao.dao;


import nowak.kamil.jpaproject.hibernate.dao.domain.AuthorDaoHibernate;
import java.util.*;

/**
 * Created by jt on 8/22/21.
 */
public interface AuthorDaoHibernateDao {

    List<AuthorDaoHibernate> findAll();

    AuthorDaoHibernate findAuthorByFirstName(final String firstName);

    List<AuthorDaoHibernate> listAuthorByLastName(final String lastName);

    AuthorDaoHibernate findAuthorByNameCriteria(String firstName, String lastName);

    AuthorDaoHibernate findAuthorByNameNative(String firstName, String lastName);

    AuthorDaoHibernate getById(Long id);

    AuthorDaoHibernate findAuthorByName(String firstName, String lastName);

    AuthorDaoHibernate findAuthorByName2(String firstName, String lastName);

    AuthorDaoHibernate saveNewAuthor(AuthorDaoHibernate author);

    AuthorDaoHibernate updateAuthor(AuthorDaoHibernate author);

    void deleteAuthorById(Long id);
}
