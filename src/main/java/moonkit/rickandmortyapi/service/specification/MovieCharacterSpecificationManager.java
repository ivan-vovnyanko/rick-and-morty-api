package moonkit.rickandmortyapi.service.specification;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import moonkit.rickandmortyapi.model.MovieCharacter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterSpecificationManager implements SpecificationManager<MovieCharacter> {
    private final Map<String, SpecificationProvider<MovieCharacter>> providersMap;

    public MovieCharacterSpecificationManager(
            List<SpecificationProvider<MovieCharacter>> providersList) {
        providersMap = providersList.stream()
                .collect(Collectors
                        .toMap(SpecificationProvider::getFilterKey, Function.identity()));
    }

    @Override
    public Specification<MovieCharacter> getSpecification(String key, String[] values) {
        if (!providersMap.containsKey(key)) {
            throw new RuntimeException("Key " + key + " is not supported for data filtering");
        }
        return providersMap.get(key).getSpecification(values);
    }
}
