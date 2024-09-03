package tacos.data.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tacos.model.TacoOrder;


public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long>{
	
	Mono<TacoOrder> save(TacoOrder order);
}
