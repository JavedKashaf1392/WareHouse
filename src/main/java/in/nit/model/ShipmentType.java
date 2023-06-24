package in.nit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "shipment_type_tab")
public class ShipmentType {
	@Id
	@GeneratedValue
	@Column(name = "ship_id_col")
	private Integer id;
	
	@Column(name = "shipment_mode_col", length = 10, nullable = false)
	private String shipmentMode;
	
	@Column(name = "shipment_code_col", length = 15, nullable = false)
	private String shipmentCode;
	
	@Column(name = "shipment_enable_col", length = 4, nullable = false)
	private String enableShipment;
	
	@Column(name = "shipment_grade_col", length = 3, nullable = false)
	private String shipmentGrade;
	
	@Column(name = "shipment_description_col", length = 150, nullable = false)
	private String description;
}
