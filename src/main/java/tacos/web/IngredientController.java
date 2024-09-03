package tacos.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.data.api.IngredientRepository;
import tacos.model.Ingredient;

@Controller
@RequestMapping(path="/api/ingredients",produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class IngredientController {

    private final IngredientRepository repo;


    public IngredientController(IngredientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients(){
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('Admin')}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<Ingredient> saveIngredient(@RequestBody Ingredient ingredient)
    {
        return repo.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('Admin')}")
    public void deleteIngredient(@PathVariable String id){
        repo.deleteById(id);
    }
}
