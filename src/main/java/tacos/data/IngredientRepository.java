package tacos.data;

import java.util.Optional;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.model.Ingredient;

//@RepositoryRestResource
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
	
	Flux<Ingredient> findAll();

	Mono<Ingredient> findById(String id);

	Mono<Ingredient> save(Ingredient ingredient);

}
