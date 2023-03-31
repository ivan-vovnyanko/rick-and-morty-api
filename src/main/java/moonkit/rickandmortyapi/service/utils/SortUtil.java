package moonkit.rickandmortyapi.service.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    public List<Sort.Order> getOrders(String value) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] fields = value.split(";");
        for (String field : fields) {
            Sort.Order order;
            if (field.contains(":")) {
                String[] fieldAndDirection = field.split(":");
                orders.add(new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                        fieldAndDirection[0]));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, field));
            }
        }
        return orders;
    }
}
