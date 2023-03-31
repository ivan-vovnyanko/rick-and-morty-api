package moonkit.rickandmortyapi.dto;

import lombok.Data;

@Data
public class MovieCharacterResponseDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
    private String species;
    private String type;
    private String image;
    private String created;
}
