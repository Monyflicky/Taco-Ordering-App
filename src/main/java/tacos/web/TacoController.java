package tacos.web;

//import org.hibernate.query.QueryParameter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import tacos.data.api.IngredientRepository;
import tacos.data.api.TacoRepository;
import tacos.data.api.UserRepository;
import tacos.model.Ingredient;
import tacos.model.Taco;

import java.net.URI;
import java.util.Arrays;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
@RequestMapping(path="/api/tacos", produces="application/json")
@CrossOrigin(origins={"http://tacocloud:8080", "http://tacocloud.com"})
public class TacoController {
    private TacoRepository tacoRep;

    TacoController(TacoRepository tacoRep){
        this.tacoRep = tacoRep;
    }

    //@GetMapping(params="recent")
    public Mono<ServerResponse> recents(ServerRequest request){
       // PageRequest page = PageRequest.of(
                //0, 12, Sort.by("createdAt").descending());
        return ServerResponse.ok().body(tacoRep.findAll(), Taco.class);
    }
    @Bean
    public RouterFunction<?> recentTaco(){
        return route(GET("/api/tacos").
                and(queryParam("recent", t->t != null )),
                this::recents)
                .andRoute(POST("/api/tacos"), this::postTaco);

    }
    @GetMapping("/{id}")
    public Mono<Taco> findTacoById(@PathVariable long id){
        return tacoRep.findById(id);
        //return tac.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    //@PostMapping(consumes = "application/json")
   // @ResponseStatus(HttpStatus.CREATED)
    public Mono<ServerResponse> postTaco(ServerRequest request)
    {
        return request.bodyToMono(Taco.class)
                .flatMap(taco -> tacoRep.save(taco))
                .flatMap(savedTaco -> {
                       return ServerResponse.
                               created(URI.create("http://localhost:8080/api/tacos/" + savedTaco.getId()))
                               .body(savedTaco, Taco.class);
                });
    }
    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository repo,
            UserRepository userRepo,
            PasswordEncoder encoder) {
        return args -> {
            Ingredient flourTortilla = new Ingredient(
                     "Flour Tortilla", Ingredient.Type.WRAP);
            Ingredient cornTortilla = new Ingredient(
                     "Corn Tortilla", Ingredient.Type.WRAP);
            Ingredient groundBeef = new Ingredient(
                    "Ground Beef", Ingredient.Type.PROTEIN);
            Ingredient carnitas = new Ingredient(
                     "Carnitas", Ingredient.Type.PROTEIN);
            Ingredient tomatoes = new Ingredient(
                    "Diced Tomatoes", Ingredient.Type.VEGGIES);
            Ingredient lettuce = new Ingredient(
                    "Lettuce", Ingredient.Type.VEGGIES);
            Ingredient cheddar = new Ingredient(
                    "Cheddar", Ingredient.Type.CHEESE);
            Ingredient jack = new Ingredient(
                    "Monterrey Jack", Ingredient.Type.CHEESE);
            Ingredient salsa = new Ingredient(
                    "Salsa", Ingredient.Type.SAUCE);
            Ingredient sourCream = new Ingredient(
                    "Sour Cream", Ingredient.Type.SAUCE);
            repo.save(flourTortilla);
            repo.save(cornTortilla);
            repo.save(groundBeef);
            repo.save(carnitas);
            repo.save(tomatoes);
            repo.save(lettuce);
            repo.save(cheddar);
            repo.save(jack);
            repo.save(salsa);
            repo.save(sourCream);

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(
                    flourTortilla, groundBeef, carnitas,
                    sourCream, salsa, cheddar));
            tacoRep.save(taco1);
            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(
                    cornTortilla, groundBeef, cheddar,
                    jack, sourCream));
            tacoRep.save(taco2);
            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(
                    flourTortilla, cornTortilla, tomatoes,
                    lettuce, salsa));
            tacoRep.save(taco3);
        };
    }
}
