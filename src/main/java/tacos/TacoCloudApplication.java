package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import tacos.data.IngredientRepository;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = "tacos.data")
@ComponentScan(basePackages = "tacos.web")
@ComponentScan(basePackages = "tacos.service")
//@ComponentScan(basePackages = "tacos.config")

public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}
	
	/*@Bean
	@Transactional
	public CommandLineRunner dataLoader(IngredientRepository repo) {

		return args -> {
			repo.deleteAll();

			//Ingredient ingd1 = new Ingredient("Flour Tortilla", Type.WRAP);
			//ingd1.setId(UUID.randomUUID().toString());
			//repo.save(ingd1);
			repo.save(new Ingredient("Corn Tortilla", Type.WRAP));
			repo.save(new Ingredient("Ground Beef", Type.PROTEIN));
			repo.save(new Ingredient("Carnitas", Type.PROTEIN));
			repo.save(new Ingredient("Diced Tomatoes", Type.VEGGIES));
			repo.save(new Ingredient("Lettuce", Type.VEGGIES));
			repo.save(new Ingredient("Cheddar", Type.CHEESE));
			repo.save(new Ingredient("Monterrey Jack", Type.CHEESE));
			repo.save(new Ingredient("Salsa", Type.SAUCE));
			repo.save(new Ingredient("Sour Cream", Type.SAUCE));

			Iterable<Ingredient> allIngredients = repo.findAll();
			for (Ingredient ingredient : allIngredients) {
				System.out.println("Ingredient: " + ingredient.getName() + " (" + ingredient.getType() + ")");
			}
		};



	}*/
}
