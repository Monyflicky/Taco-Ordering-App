package tacos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

//import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name= "tacoorders")
public class TacoOrder  implements Serializable {
	 
	 private static final long serialVersionUID = 1L;
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private Date placedAt; 
	 
	 @NotBlank(message="Delivery Name is required")
	 private String deliveryName;
	 
	 @NotBlank(message="Street is required")
	 private String deliveryStreet;
	 
	 @NotBlank(message="City required")
	 private String deliveryCity;
	 
	 @NotBlank(message="State is required")
	 private String deliveryState;
	 
	 @NotBlank(message="Zip is required")
	 private String deliveryZip;
	 
	 @CreditCardNumber(message="Not a valid credit card")
	 private String ccNumber;
	 
	 @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
			 message="Must be formatted MM/YY")
	 private String ccExpiration;
	 
	 @Digits(integer=3, fraction=0, message="Invalid CVV")
	 private String ccCVV;

    @NotNull
    @Size(min=1, message="You must choose at least one ingredient")
    //@ManyToMany()
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne
    private User user;
	 
	 public void addTaco(Taco taco) {
		 this.tacos.add(taco);
	 }
	 
	 public List<Taco> getTacos() {
	        return tacos;
	 }
	 
	 public String getDeliveryName() {
	        return deliveryName;
	    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public Long getId() {
        return id;
    }

   
    public void setId(Long id) {
        this.id = id;
    }

    
    public Date getPlacedAt() {
        return placedAt;
    }

   
    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TacoOrder{");
        sb.append("deliveryName=").append(deliveryName);
        sb.append(", deliveryStreet=").append(deliveryStreet);
        sb.append("deliveryCity=").append(deliveryCity);
        sb.append(", deliveryState=").append(deliveryState);
        sb.append("deliveryZip=").append(deliveryZip);
        sb.append(", ccNumber=").append(ccNumber);
        sb.append(", ccExpiration=").append(ccExpiration);
        sb.append(", ccCVV=").append(ccCVV);
        sb.append(", tacos=");
        // Include other fields as needed
        for (Taco taco : tacos) {
        	sb.append(taco.toString());
        }
        sb.append('}');
        return sb.toString();
    }

}
