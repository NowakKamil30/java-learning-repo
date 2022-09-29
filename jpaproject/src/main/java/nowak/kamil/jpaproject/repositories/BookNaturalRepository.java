package nowak.kamil.jpaproject.repositories;

import nowak.kamil.jpaproject.domain.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
