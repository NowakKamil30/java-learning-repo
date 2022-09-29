package nowak.kamil.jpaproject.hibernate.dao.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import nowak.kamil.jpaproject.hibernate.dao.domain.AuthorDaoHibernate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class AuthorDaoHibernateDaoImpl implements AuthorDaoHibernateDao {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<AuthorDaoHibernate> findAll() {
        final var entityManager = getEntityManager();

        try {
            TypedQuery<AuthorDaoHibernate> typedQuery = entityManager.createNamedQuery("author_find_all", AuthorDaoHibernate.class);

            return typedQuery.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //typed query
    @Override
    public AuthorDaoHibernate findAuthorByFirstName(String firstName) {
        final var entityManager = getEntityManager();

        try {
            final var query = entityManager.createQuery("SELECT a from AuthorDaoHibernate a WHERE a.firstName = :first_name", AuthorDaoHibernate.class);
            query.setParameter("first_name", firstName);

            return query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<AuthorDaoHibernate> listAuthorByLastName(String lastName) {
        final var entityManager = getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT a from AuthorDaoHibernate a where a.lastName like :last_name");
            query.setParameter("last_name", lastName);
            List<AuthorDaoHibernate> authorDaoHibernates = query.getResultList();
            return authorDaoHibernates;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AuthorDaoHibernate findAuthorByNameCriteria(String firstName, String lastName) {
        final var entityManager = getEntityManager();

        try {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<AuthorDaoHibernate> criteriaQuery = criteriaBuilder.createQuery(AuthorDaoHibernate.class);
            final Root<AuthorDaoHibernate> root = criteriaQuery.from(AuthorDaoHibernate.class);
            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);
            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);
            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<AuthorDaoHibernate> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);

            return typedQuery.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AuthorDaoHibernate findAuthorByNameNative(String firstName, String lastName) {
        final var entityManager = getEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM author_dao_hibernate where first_name = ? and " +
                    "last_name = ?", AuthorDaoHibernate.class);
            query.setParameter("first_name", firstName);
            query.setParameter("last_name", lastName);

            return (AuthorDaoHibernate) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AuthorDaoHibernate getById(Long id) {
        final var entityManager = getEntityManager();
        AuthorDaoHibernate authorDaoHibernate = entityManager.find(AuthorDaoHibernate.class, id);
        entityManager.close();
        return authorDaoHibernate;
    }

    @Override
    public AuthorDaoHibernate findAuthorByName(String firstName, String lastName) {
        final var entityManager = getEntityManager();
        final TypedQuery<AuthorDaoHibernate> query = entityManager.createQuery("select a from AuthorDaoHibernate a " +
                "WHERE a.firstName = :first_name and a.lastName = :last_name", AuthorDaoHibernate.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        AuthorDaoHibernate singleResult = query.getSingleResult();
        entityManager.close();
        return singleResult;
    }

    @Override
    public AuthorDaoHibernate findAuthorByName2(String firstName, String lastName) {
        final var entityManager = getEntityManager();
        final TypedQuery<AuthorDaoHibernate> query = entityManager.createNamedQuery("find_by_name", AuthorDaoHibernate.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        AuthorDaoHibernate singleResult = query.getSingleResult();
        entityManager.close();
        return singleResult;
    }

    @Override
    public AuthorDaoHibernate saveNewAuthor(AuthorDaoHibernate author) {
        final var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return author;
    }

    @Override
    public AuthorDaoHibernate updateAuthor(AuthorDaoHibernate author) {
        final var entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();
        AuthorDaoHibernate authorDaoHibernate = entityManager.find(AuthorDaoHibernate.class, author.getId());
        entityManager.close();
        return authorDaoHibernate;
    }

    @Override
    public void deleteAuthorById(Long id) {
        final var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        final var authorDaoHibernate = entityManager.find(AuthorDaoHibernate.class, id);

        entityManager.remove(authorDaoHibernate);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
