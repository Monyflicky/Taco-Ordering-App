package tacos.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tacos.data.IngredientRepository;
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
    public Iterable<Ingredient> allIngredients(){
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('Admin')}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient)
    {
        return repo.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('Admin')}")
    public void deleteIngredient(@PathVariable("id") String id){
        repo.deleteById(id);
    }
}
