package moonkit.rickandmortyapi.dto.api;

import lombok.Data;

@Data
public class ApiInfoDto {
    private Integer count;
    private Integer pages;
    private String next;
    private String prev;
}
