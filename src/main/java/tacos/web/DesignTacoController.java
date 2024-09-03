package tacos.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import jakarta.validation.Valid;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import tacos.data.api.IngredientRepository;
import tacos.data.internal.IngredientOtherRepository;
import tacos.model.Ingredient;
import tacos.model.Taco;
import tacos.model.TacoOrder;
import tacos.model.Ingredient.Type;


//@Slf4j
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@Controller
public class DesignTacoController {
	
	private static final Logger log = LoggerFactory.getLogger(DesignTacoController.class);
	RestTemplate rest = new RestTemplate();
	
	private IngredientRepository ingredientRepo;
	
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@ModelAttribute
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void addIngredientsToModel(Model model) {
				Flux<Ingredient> ingredients = ingredientRepo.findAll();
						
				Type[] types = Ingredient.Type.values();
				for(Type type : types) {
					model.addAttribute(type.toString().toLowerCase(), 
							filterByType(ingredients, type));
				}
	}
	@ModelAttribute(name = "tacoOrder")
	 public TacoOrder order() {
		return new TacoOrder();
	 }

	 @ModelAttribute(name = "taco")
	 public Taco taco() {
		 return new Taco();
	 }
	 
	 @GetMapping
	 public String showDesignForm() {
		 return "design";
	 }
	 @PostMapping
	 public String processTaco(@Valid Taco taco, Errors errors ,@ModelAttribute TacoOrder tacoOrder) {
		 if(errors.hasErrors()) {
			 return "design";
		 }
		 tacoOrder.addTaco(taco);
		 log.info("Processing taco: {}", taco);
		 
		 
		 return "redirect:/orders/current";
	 }
	/*private Iterable<Ingredient> filterByType(
			 Iterable<Ingredient> ingredients, Type type) {
			 return ingredients
			 .stream()
			 .filter(x -> x.getType().equals(type))
			 .collect(Collectors.toList());
	}*/
	//The method below should work with data rest
	public Ingredient getIngredientById(String ingredientId) {
		return rest.getForObject("http://localhost:8080/ingredients/{id}",
				Ingredient.class, ingredientId);
	}
//	 private Iterable<Ingredient> filterByType(Flux<Ingredient> ingredients, Type type) {
//		    return StreamSupport.stream(ingredients.spliterator(), false)
//		            .filter(x -> x.getType().equals(type))
//		            .collect(Collectors.toList());
//	}
	private Flux<Ingredient> filterByType(Flux<Ingredient> ingredients, Type type) {
		return ingredients
				.filter(ingredient -> ingredient.getType().equals(type));
	}


}
