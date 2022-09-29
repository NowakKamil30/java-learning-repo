package nowak.kamil.jpaproject.hibernate.dao.repositories;

import nowak.kamil.jpaproject.hibernate.dao.domain.BookDaoHibernate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDaoHibernateRepository extends JpaRepository<BookDaoHibernate, Long> {
}
