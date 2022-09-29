package nowak.kamil.jpaproject.hibernate.dao.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import nowak.kamil.jpaproject.hibernate.dao.domain.BookDaoHibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BookDao {
    private final EntityManagerFactory entityManagerFactory;

    public List<BookDaoHibernate> findAllBooks(Pageable pageable) {
        final var entityManager = getEntityManager();

        try {
            final var query = entityManager.createQuery("SELECT b FROM BookDaoHibernate b", BookDaoHibernate.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<BookDaoHibernate> findAllBooksSortedByTitle(final Pageable pageable) {
        final var entityManager = getEntityManager();

        try {
            final var hql = "SELECT b FROM BookDaoHibernate b order by b.title " + pageable.getSort()
                    .getOrderFor("title")
                    .getDirection()
                    .name();
            final var query =  entityManager.createQuery(hql, BookDaoHibernate.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }


    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
