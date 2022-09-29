package nowak.kamil.jpaproject.repositories;

import nowak.kamil.jpaproject.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
