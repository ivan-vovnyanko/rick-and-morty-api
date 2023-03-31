package moonkit.rickandmortyapi.service.specification.character;

import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.Objects;
import moonkit.rickandmortyapi.model.MovieCharacter;
import moonkit.rickandmortyapi.model.Status;
import moonkit.rickandmortyapi.service.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterStatusInProvider implements SpecificationProvider<MovieCharacter> {
    private static final String FILTER_KEY = "statusIn";
    private static final String FILTER_FIELD = "status";

    @Override
    public Specification<MovieCharacter> getSpecification(String[] values) {
        return ((root, query, cb) -> {
            CriteriaBuilder.In<Status> predicate = cb.in(root.get(FILTER_FIELD));
            if (Objects.equals(values[0], "")) {
                for (Status status : Status.values()) {
                    predicate.value(status);
                }
                return cb.and(predicate);
            }
            for (String value : values) {
                Status status = Status.valueOf(value.toUpperCase());
                predicate.value(status);
            }
            return cb.and(predicate);
        });
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
