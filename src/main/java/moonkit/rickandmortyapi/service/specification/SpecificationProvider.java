package moonkit.rickandmortyapi.service.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    Specification<T> getSpecification(String[] values);

    String getFilterKey();
}
