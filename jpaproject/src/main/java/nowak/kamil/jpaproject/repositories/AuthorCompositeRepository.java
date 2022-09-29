package nowak.kamil.jpaproject.repositories;

import nowak.kamil.jpaproject.domain.authorcomposite.AuthorComposite;
import nowak.kamil.jpaproject.domain.authorcomposite.AuthorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, AuthorId> {
}
