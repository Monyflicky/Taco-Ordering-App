package tacos.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;
import tacos.model.TacoOrder;
import tacos.model.User;

import java.security.Principal;

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
	public TacoOrder putOrder(
			@PathVariable("orderId") Long orderId,
			@RequestBody TacoOrder order) {
		order.setId(orderId);
		return orderRepo.save(order);
	}

	@PatchMapping(path="/{orderId}", consumes="application/json")
	public TacoOrder patchOrder(@PathVariable("orderId") Long orderId,
								@RequestBody TacoOrder patch) {
		TacoOrder order = orderRepo.findById(orderId).get();
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
	}
}
