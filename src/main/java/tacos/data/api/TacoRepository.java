package tacos.data.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.model.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
