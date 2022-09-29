package nowak.kamil.jpaproject.repositories;

import nowak.kamil.jpaproject.domain.composite.BookComposite;
import nowak.kamil.jpaproject.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCompositeRepository extends JpaRepository<BookComposite, NameId> {
}
