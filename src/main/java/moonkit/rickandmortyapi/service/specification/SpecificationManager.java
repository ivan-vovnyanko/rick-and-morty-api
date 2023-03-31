package moonkit.rickandmortyapi.service.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationManager<T> {
    Specification<T> getSpecification(String key, String[] values);
}
