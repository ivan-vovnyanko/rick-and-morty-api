package moonkit.rickandmortyapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movie_character")
public class MovieCharacter {
    @Id
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String species;
    private String type;
    private String apiImageUrl;
    private String localImageUrl;
    private String created;
}
