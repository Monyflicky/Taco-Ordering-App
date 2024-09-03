package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import tacos.data.api.IngredientRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TacoCloudApplicationTests {
	@Autowired
	private IngredientRepository ingredientRepo;

	@Test
	void contextLoads() {
		assertNotNull(ingredientRepo);
	}

	@Test
	public void createAFlux_just() {
		String[] fruits = new String[] {"mango", "lemon","orange","apple", "grape", "cherries"};
		Flux<String> fruiteFlux = Flux.fromArray(fruits);

		StepVerifier.create(fruiteFlux)
				.expectNext("mango")
				.expectNext("lemon")
				.expectNext("orange")
				.expectNext("apple")
				.expectNext("grape")
				.expectNext("cherries")
				.verifyComplete();

	}
	@Test
	public void bufferAndFlatMap() throws Exception {
		Flux.just(
						"apple", "orange", "banana", "kiwi", "strawberry")
				.buffer(3)
				.flatMap(x ->
						Flux.fromIterable(x)
								.map(y -> y.toUpperCase())
								.subscribeOn(Schedulers.parallel())
								.log()
				).subscribe();
	}
}
