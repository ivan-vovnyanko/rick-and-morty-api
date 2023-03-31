package moonkit.rickandmortyapi.service;

import java.util.Map;
import moonkit.rickandmortyapi.model.MovieCharacter;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandom();

    Page<MovieCharacter> getAllByParams(Map<String, String> params);

    MovieCharacter getById(Long id);

    Resource getResourceById(Long id);
}
