package tacos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.GenericGenerator;

//import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;



@Data
@Entity
@Table(name="ingredients")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "Id", columnDefinition = "VARCHAR(100)")
    public String id;
    
    public String name;

    public Type type;
    
    public Ingredient(String name, Type type) {
        this.name = name;
        this.type = type;
        //this.id = this.id = UUID.randomUUID().toString();
    }


    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

  
    public String getId() {
        return id;
    }

   
   /* public void setId(String id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

   
    public void setName(String name) {
        this.name = name;
    }

   
    public Type getType() {
        return type;
    }

  
    public void setType(Type type) {
        this.type = type;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredient{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append("type=").append(type);
        sb.append('}');
        return sb.toString();
    }
	
}
