package tacos.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tacos.data.OrderMessagingService;
import tacos.data.OrderRepository;
import tacos.model.TacoOrder;

@RestController
@RequestMapping(path="/api/orders",
        produces="application/json")
@CrossOrigin(origins="http:/ /localhost:8080")
public class OrderApiController
{
    private final OrderRepository repo;
    private final OrderMessagingService messageService;

    public OrderApiController(
            OrderRepository repo,
            OrderMessagingService messageService) {
        this.repo = repo;
        this.messageService = messageService;
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postTacoOrder(@RequestBody TacoOrder order){

        messageService.sendOrder(order);
        return repo.save(order);
    }


}
