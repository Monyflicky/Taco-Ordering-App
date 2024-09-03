package tacos.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;
import tacos.data.api.OrderRepository;
import tacos.data.api.UserRepository;
import tacos.model.TacoOrder;
import tacos.model.User;

//@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	private final OrderRepository orderRepo;
	private final UserRepository userRepository;
    
	public OrderController(OrderRepository orderRepo, UserRepository userRepository) {
		this.orderRepo = orderRepo;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid TacoOrder order, Errors errors,
							   SessionStatus sessionStatus
			, @AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		order.setUser(user);
	 log.info("Order submitted: {}", order);
	 orderRepo.save(order);
	 sessionStatus.setComplete();
	 return "redirect:/";
	}
	@PutMapping(path="/{orderId}", consumes="application/json")
	public Mono<TacoOrder> putOrder(
			@PathVariable Long orderId,
			@RequestBody TacoOrder order) {
		order.setId(orderId);
		return orderRepo.save(order);
	}

	@PatchMapping(path="/{orderId}", consumes="application/json")
	public Mono<TacoOrder> patchOrder(@PathVariable Long orderId,
								@RequestBody TacoOrder patch) {
		return orderRepo.findById(orderId)
				.flatMap(order -> {
					if (patch.getDeliveryName() != null) {
						order.setDeliveryName(patch.getDeliveryName());
					}
					if (patch.getDeliveryStreet() != null) {
						order.setDeliveryStreet(patch.getDeliveryStreet());
					}
					if (patch.getDeliveryCity() != null) {
						order.setDeliveryCity(patch.getDeliveryCity());
					}
					if (patch.getDeliveryState() != null) {
						order.setDeliveryState(patch.getDeliveryState());
					}
					if (patch.getDeliveryZip() != null) {
						order.setDeliveryZip(patch.getDeliveryZip());
					}
					if (patch.getCcNumber() != null) {
						order.setCcNumber(patch.getCcNumber());
					}
					if (patch.getCcExpiration() != null) {
						order.setCcExpiration(patch.getCcExpiration());
					}
					if (patch.getCcCVV() != null) {
						order.setCcCVV(patch.getCcCVV());
					}
					return orderRepo.save(order);
				});

		//return orderRepo.save(order);
	}
}
