package moonkit.rickandmortyapi.service.specification.character;

import jakarta.persistence.criteria.Predicate;
import moonkit.rickandmortyapi.model.MovieCharacter;
import moonkit.rickandmortyapi.service.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterNameContainsIn implements SpecificationProvider<MovieCharacter> {
    private static final String FILTER_KEY = "name";
    private static final String FILTER_FIELD = "name";

    @Override
    public Specification<MovieCharacter> getSpecification(String[] values) {
        return ((root, query, cb) -> {
            Predicate like = cb.like(root.get(FILTER_FIELD), values[0] + "%");
            return cb.and(like);
        });
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
