package moonkit.rickandmortyapi.service.specification.character;

import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.Objects;
import moonkit.rickandmortyapi.model.Gender;
import moonkit.rickandmortyapi.model.MovieCharacter;
import moonkit.rickandmortyapi.service.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterGenderInProvider implements SpecificationProvider<MovieCharacter> {
    private static final String FILTER_KEY = "genderIn";
    private static final String FILTER_FIELD = "gender";

    @Override
    public Specification<MovieCharacter> getSpecification(String[] values) {
        return ((root, query, cb) -> {
            CriteriaBuilder.In<Gender> predicate = cb.in(root.get(FILTER_FIELD));
            if (Objects.equals(values[0], "")) {
                for (Gender gender : Gender.values()) {
                    predicate.value(gender);
                }
                return cb.and(predicate);
            }
            for (String value : values) {
                Gender gender = Gender.valueOf(value.toUpperCase());
                predicate.value(gender);
            }
            return cb.and(predicate);
        });
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
