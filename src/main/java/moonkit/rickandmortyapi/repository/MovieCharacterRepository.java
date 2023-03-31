package moonkit.rickandmortyapi.repository;

import moonkit.rickandmortyapi.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCharacterRepository
        extends JpaRepository<MovieCharacter, Long>, JpaSpecificationExecutor<MovieCharacter> {
    @Query("SELECT COUNT(mc) FROM MovieCharacter mc")
    Integer getCountOfCharacters();
}
