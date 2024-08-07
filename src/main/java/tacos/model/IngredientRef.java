package tacos.model;


import jakarta.persistence.*;
import lombok.Data;
//import org.springframework.data.relational.core.mapping.Table;

@Data
@Entity
@Table(name="ingredientrefs")
public class IngredientRef {
	
	@Id
    //@ManyToOne
    //@JoinColumn(name = "Id")
    public String ingredient;
	
	
	public IngredientRef(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String Ingredient) {
        this.ingredient = Ingredient;
    }

}
