package moonkit.rickandmortyapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import moonkit.rickandmortyapi.dto.MovieCharacterResponseDto;
import moonkit.rickandmortyapi.mapper.MovieCharacterMapper;
import moonkit.rickandmortyapi.service.MovieCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    @Autowired
    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper mapper) {
        this.movieCharacterService = movieCharacterService;
        this.mapper = mapper;
    }

    @Operation(summary = "Endpoint that gives a random character from the connected database")
    @GetMapping("/random")
    public MovieCharacterResponseDto getRandom() {
        return mapper.toDto(movieCharacterService.getRandom());
    }

    @Operation(summary = "This is an endpoint that gives a list of characters that are "
            + "filtered by several parameters. There are sorting by fields, pagination and "
            + "filtering by fields such as name, gender or status.")
    @GetMapping("/search")
    public List<MovieCharacterResponseDto> search(
            @RequestParam (defaultValue = "0")
            @Parameter (description = "Default value '0'") String page,
            @RequestParam (defaultValue = "id")
            @Parameter (description = "Default value 'id'") String sortBy,
            @RequestParam (defaultValue = "")
            @Parameter (description = "Default value is ''") String name,
            @RequestParam (defaultValue = "")
            @Parameter (description = "Default value is ''") String genderIn,
            @RequestParam (defaultValue = "")
            @Parameter(description = "Default value is ''") String statusIn) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("sortBy", sortBy);
        params.put("name", name);
        params.put("genderIn", genderIn);
        params.put("statusIn", statusIn);
        return movieCharacterService.getAllByParams(params)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Endpoint that gives a character by id from the connected database")
    @GetMapping("/{id}")
    public MovieCharacterResponseDto getById(
            @PathVariable @Parameter(description = "Character id") Long id) {
        return mapper.toDto(movieCharacterService.getById(id));
    }

    @Operation(summary = "Endpoint that gives a avatar (jpeg image) by character id from the "
            + "connected database")
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImageById(
            @PathVariable @Parameter(description = "Character id") Long id) {
        Resource resource = movieCharacterService.getResourceById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + resource.getFilename() + "\"")
                .body(resource);
    }
}
