package tacos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

//import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="tacos")
public class Taco implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

	private Date createdAt = new Date();

	@NotNull
	@Size(min=1, message="You must choose atleast one ingredient")
    @ManyToMany()
    //@OneToMany(cascade = CascadeType.PERSIST)
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
       // sb.append("Taco{");
        sb.append("name=").append(name);
        for (Ingredient ingredient : ingredients) {
        	sb.append(ingredient.toString());
        }
        sb.append('}');
        return sb.toString();
    }
}
