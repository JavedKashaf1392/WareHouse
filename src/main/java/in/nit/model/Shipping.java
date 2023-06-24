package in.nit.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="shippingipping_tab")
public class Shipping {

	@Id
	@GeneratedValue
	@Column(name="shipping_id_col")
	private Integer id;
	
	@Column(name="shipping_code_col")
	private String shippingCode;

	@Column(name="shipping_refNum_col")
	private String shippingRefNum;

	@Column(name="shipping_courier_col")
	private String courierRefNum;

	@Column(name="shipping_contact_col")
	private String contact;

	@Column(name="shipping_so_code_col")
	private String soCode;

	@Column(name="shipping_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "so_id_fk", unique = true)
	private SaleOrder so;
	
	@OneToMany(mappedBy = "shipping")
	private List<ShippingDtl> dtls;
}
