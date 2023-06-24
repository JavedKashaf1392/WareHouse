package in.nit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.Data;

@Data
@Entity
@Table(name="shipping_dtl_tab")
public class ShippingDtl {

	@Id
	@GeneratedValue
	@Column(name="shipping_dtl_id")
	private Integer id;

	@Column(name="shipping_dtl_code")
	private String itemCode;

	@Column(name="shipping_dtl_cost")
	private Double baseCost;

	@Column(name="shipping_dtl_qty")
	private Integer qty;

	@Column(name="shipping_dtl_value")
	private Double itemValue;
	
	@Column(name="shipping_dtl_status")
	private String status;

	@ManyToOne
	@JoinColumn(name="shipping_id_fk")
	private Shipping shipping;
	
}
