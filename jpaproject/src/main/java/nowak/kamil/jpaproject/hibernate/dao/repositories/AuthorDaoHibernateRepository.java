package nowak.kamil.jpaproject.hibernate.dao.repositories;


import nowak.kamil.jpaproject.hibernate.dao.domain.AuthorDaoHibernate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDaoHibernateRepository extends JpaRepository<AuthorDaoHibernate, Long> {
}
