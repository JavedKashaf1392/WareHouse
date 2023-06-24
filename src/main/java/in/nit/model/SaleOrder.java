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
@Table(name = "sale_order_tab")
public class SaleOrder {
	@Id
	@GeneratedValue
	@Column(name = "sal_id_col")
	private Integer id;
	@Column(name = "sal_code_col")
	private String orderCode;

	@Column(name = "sal_refNum_col")
	private String referenceNumber;

	@Column(name = "sal_mode_col")
	private String stockMode;

	@Column(name = "sal_source_col")
	private String stockSource;

	@Column(name = "sal_status_col")
	private String status;

	@Column(name = "sal_desc_col")
	private String description;

	@ManyToOne
	@JoinColumn(name = "porder_shipCode_fk")
	private ShipmentType shipmentCode;

	@ManyToOne
	@JoinColumn(name = "porder_wu_vendor_fk")
	private WhUserType customer;
	
	@OneToMany(mappedBy = "so")
	private List<SaleDtl>Dtls;
}
