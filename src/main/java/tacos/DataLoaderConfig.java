package tacos;

/*import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tacos.data.IngredientRepository;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

@Component
@Configuration
public class DataLoaderConfig implements CommandLineRunner {

    private final IngredientRepository repo;

    public DataLoaderConfig(IngredientRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        repo.deleteAll();

        repo.save(new Ingredient("Flour Tortilla", Ingredient.Type.WRAP));
        repo.save(new Ingredient("Corn Tortilla", Ingredient.Type.WRAP));
        repo.save(new Ingredient("Ground Beef", Type.PROTEIN));
        repo.save(new Ingredient("Carnitas", Type.PROTEIN));
        repo.save(new Ingredient("Diced Tomatoes", Type.VEGGIES));
        repo.save(new Ingredient("Lettuce", Type.VEGGIES));
        repo.save(new Ingredient("Cheddar", Type.CHEESE));
        repo.save(new Ingredient("Monterrey Jack", Type.CHEESE));
        repo.save(new Ingredient("Salsa", Type.SAUCE));
        repo.save(new Ingredient("Sour Cream", Type.SAUCE));

        Iterable<Ingredient> allIngredients = repo.findAll();
    }
}
*/