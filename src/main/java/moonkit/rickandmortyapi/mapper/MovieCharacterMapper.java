package moonkit.rickandmortyapi.mapper;

import java.io.File;
import moonkit.rickandmortyapi.dto.MovieCharacterResponseDto;
import moonkit.rickandmortyapi.dto.api.ApiCharacterDto;
import moonkit.rickandmortyapi.model.Gender;
import moonkit.rickandmortyapi.model.MovieCharacter;
import moonkit.rickandmortyapi.model.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    @Value("${custom.images-folder}")
    private String resourcesFolder;

    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setApiImageUrl(dto.getImage());
        movieCharacter.setSpecies(dto.getSpecies());
        movieCharacter.setCreated(dto.getCreated());
        movieCharacter.setType(dto.getType());
        movieCharacter.setLocalImageUrl(File.separator + resourcesFolder + File.separator
                + "character_images" + File.separator + dto.getId() + "."
                + dto.getImage().split("\\.")[dto.getImage().split("\\.").length - 1]);
        return movieCharacter;
    }

    public MovieCharacterResponseDto toDto(MovieCharacter movieCharacter) {
        MovieCharacterResponseDto movieCharacterResponseDto = new MovieCharacterResponseDto();
        movieCharacterResponseDto.setId(movieCharacter.getId());
        movieCharacterResponseDto.setName(movieCharacter.getName());
        movieCharacterResponseDto.setGender(movieCharacter.getGender().getValue());
        movieCharacterResponseDto.setStatus(movieCharacter.getStatus().getValue());
        movieCharacterResponseDto.setCreated(movieCharacter.getCreated());
        movieCharacterResponseDto.setType(movieCharacter.getType());
        movieCharacterResponseDto.setSpecies(movieCharacter.getSpecies());
        movieCharacterResponseDto.setImage(movieCharacter.getLocalImageUrl());
        return movieCharacterResponseDto;
    }
}
