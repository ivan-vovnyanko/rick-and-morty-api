package moonkit.rickandmortyapi.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import moonkit.rickandmortyapi.dto.MovieCharacterResponseDto;
import moonkit.rickandmortyapi.mapper.MovieCharacterMapper;
import moonkit.rickandmortyapi.model.Gender;
import moonkit.rickandmortyapi.model.MovieCharacter;
import moonkit.rickandmortyapi.model.Status;
import moonkit.rickandmortyapi.service.MovieCharacterService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
class MovieCharacterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieCharacterService movieCharacterService;

    @MockBean
    private MovieCharacterMapper movieCharacterMapper;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldReturnRandomMovieCharacter_Ok() {
        MovieCharacter mockCharacter = new MovieCharacter();
        mockCharacter.setId(142L);
        mockCharacter.setType("");
        mockCharacter.setSpecies("");
        mockCharacter.setCreated("11.11.2011");
        mockCharacter.setGender(Gender.FEMALE);
        mockCharacter.setName("Beth");
        mockCharacter.setStatus(Status.ALIVE);
        Mockito.when(movieCharacterService.getRandom()).thenReturn(mockCharacter);
        MovieCharacterResponseDto mockDto = new MovieCharacterResponseDto();
        mockDto.setId(142L);
        mockDto.setType("");
        mockDto.setSpecies("");
        mockDto.setCreated("04.08.1989");
        mockDto.setGender("Female");
        mockDto.setName("Beth");
        mockDto.setStatus("Alive");
        Mockito.when(movieCharacterMapper.toDto(mockCharacter)).thenReturn(mockDto);
        RestAssuredMockMvc
                .get("/movie-characters/random")
                .then()
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(142))
                .body("created", Matchers.equalTo("04.08.1989"))
                .body("type", Matchers.equalTo(""))
                .body("name", Matchers.equalTo("Beth"))
                .body("species", Matchers.equalTo(""))
                .body("status", Matchers.equalTo("Alive"))
                .body("gender", Matchers.equalTo("Female"));
    }

    @Test
    void shouldCharacterById() {
        MovieCharacter morty = new MovieCharacter();
        morty.setId(32L);
        morty.setType("");
        morty.setSpecies("");
        morty.setCreated("24.02.2004");
        morty.setGender(Gender.MALE);
        morty.setName("Morty");
        morty.setStatus(Status.ALIVE);
        Mockito.when(movieCharacterService.getById(32L)).thenReturn(morty);
        MovieCharacterResponseDto mockDto = new MovieCharacterResponseDto();
        mockDto.setId(32L);
        mockDto.setType("");
        mockDto.setSpecies("");
        mockDto.setCreated("24.02.2004");
        mockDto.setGender("Male");
        mockDto.setName("Morty");
        mockDto.setStatus("Alive");
        Mockito.when(movieCharacterMapper.toDto(morty)).thenReturn(mockDto);
        RestAssuredMockMvc
                .get("/movie-characters/32")
                .then()
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(32))
                .body("created", Matchers.equalTo("24.02.2004"))
                .body("type", Matchers.equalTo(""))
                .body("name", Matchers.equalTo("Morty"))
                .body("species", Matchers.equalTo(""))
                .body("status", Matchers.equalTo("Alive"))
                .body("gender", Matchers.equalTo("Male"));
    }
}