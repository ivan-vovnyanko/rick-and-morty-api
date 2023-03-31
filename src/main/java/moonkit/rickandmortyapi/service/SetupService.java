package moonkit.rickandmortyapi.service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!test")
@Log4j2
@Service
public class SetupService {
    private final MovieCharacterService movieCharacterService;

    @Autowired
    public SetupService(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @PostConstruct
    public void init() throws IOException {
        log.info("The data was updated in the database when the application started");
        movieCharacterService.syncExternalCharacters();
    }
}
