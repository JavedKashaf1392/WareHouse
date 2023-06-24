package in.nit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="uom_tab")
public class Uom {
	
	@Id
	@GeneratedValue
	@Column(name="uom_id_col")
	private Integer id;
	
	@NotNull(message = "Uom Type can not be null")
	@Column(name="uom_type_col", length = 15, nullable = false)
	private String uomType;
	
	@NotNull(message = "Uom Model can not be null")
	@Pattern(regexp = "[A-Z]{2,10}",message = "Invalid Pattern Entered.")
	@Column(name="uom_model_col", length = 10, nullable = false)
	private String uomModel;
	
	@Size(min=3, max=50,message = "Length must be 3-50 chars.")
	@Column(name="description_col", length = 150, nullable = false)
	private String description;
}












