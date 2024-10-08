package tacos.data.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tacos.model.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long>
{
    Mono<User> findByUsername(String username);
}
