package nowak.kamil.jpaproject.dao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nowak.kamil.jpaproject.domain.Author;
import nowak.kamil.jpaproject.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(Author author) {
        final var foundAuthor = getById(author.getId());
        foundAuthor.setFirstName(author.getFirstName());
        foundAuthor.setLastName(author.getLastName());
        return  authorRepository.save(foundAuthor);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
