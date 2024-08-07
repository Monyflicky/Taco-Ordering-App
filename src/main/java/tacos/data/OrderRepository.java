package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tacos.model.TacoOrder;


public interface OrderRepository extends CrudRepository<TacoOrder, Long>{
	
	TacoOrder save(TacoOrder order);
}
