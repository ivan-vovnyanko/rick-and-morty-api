package moonkit.rickandmortyapi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import moonkit.rickandmortyapi.dto.api.ApiResponseDto;
import moonkit.rickandmortyapi.mapper.MovieCharacterMapper;
import moonkit.rickandmortyapi.model.MovieCharacter;
import moonkit.rickandmortyapi.repository.MovieCharacterRepository;
import moonkit.rickandmortyapi.service.MovieCharacterService;
import moonkit.rickandmortyapi.service.specification.SpecificationManager;
import moonkit.rickandmortyapi.service.utils.Downloader;
import moonkit.rickandmortyapi.service.utils.HttpClient;
import moonkit.rickandmortyapi.service.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private static final String CHARACTERS_URL = "https://rickandmortyapi.com/api/character";
    private static final String IMAGE_DIRECTORY = "character_images";
    private static final String SORT_KEY = "sortBy";
    private static final String PAGE_KEY = "page";
    private static final int DEFAULT_SIZE = 20;
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper mapper;
    private final Downloader downloader;
    private final SortUtil sortUtil;
    private final SpecificationManager<MovieCharacter> specificationManager;

    @Autowired
    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository,
                                     MovieCharacterMapper mapper,
                                     Downloader downloader,
                                     SortUtil sortUtil,
                                     SpecificationManager<MovieCharacter> specificationManager) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.mapper = mapper;
        this.downloader = downloader;
        this.sortUtil = sortUtil;
        this.specificationManager = specificationManager;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient
                .get(CHARACTERS_URL, ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        saveImages(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
            saveImages(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandom() {
        return movieCharacterRepository.getReferenceById((long) new Random()
                .nextInt(1 + movieCharacterRepository.getCountOfCharacters()));
    }

    @Override
    public Page<MovieCharacter> getAllByParams(Map<String, String> params) {
        List<Sort.Order> orders = new ArrayList<>();
        if (params.get(SORT_KEY) != null) {
            orders.addAll(sortUtil.getOrders(params.get(SORT_KEY)));
        }
        Sort sort = Sort.by(orders);
        PageRequest pageRequest =
                PageRequest.of(Integer.parseInt(params.get(PAGE_KEY)), DEFAULT_SIZE, sort);
        Specification<MovieCharacter> spec = null;
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (!Objects.equals(param.getKey(), SORT_KEY)
                    && !Objects.equals(param.getKey(), PAGE_KEY)) {
                Specification<MovieCharacter> sp = specificationManager
                        .getSpecification(param.getKey(), param.getValue().split(","));
                spec = spec == null
                        ? Specification.where(sp) : spec.and(sp);
            }
        }
        return movieCharacterRepository.findAll(spec, pageRequest);
    }

    @Override
    public MovieCharacter getById(Long id) {
        return movieCharacterRepository.getReferenceById(id);
    }

    private void saveDtosToDb(ApiResponseDto apiResponseDto) {
        movieCharacterRepository
                .saveAllAndFlush(Arrays.stream(apiResponseDto.getResults())
                        .map(mapper::parseApiCharacterResponseDto)
                        .collect(Collectors.toList()));
    }

    private void saveImages(ApiResponseDto apiResponseDto) {
        Arrays.stream(apiResponseDto.getResults()).forEach(c -> {
            downloader.downloadFile(c.getImage(), IMAGE_DIRECTORY, String.valueOf(c.getId()),
                    1);
        });
    }
}
