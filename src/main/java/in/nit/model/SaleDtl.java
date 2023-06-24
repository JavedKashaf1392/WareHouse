package in.nit.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name="sales_dtl_tab")
public class SaleDtl {

	@Id
	@GeneratedValue
	@Column(name="sal_dtl_id_col")
	private Integer id;
	
	@Transient
	private Integer slno;
	
	@Column(name="sal_dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name="part_id_fk")
	private Part part; //HAS-A
	
	@ManyToOne
	@JoinColumn(name="so_id_fk")
	private SaleOrder  so; //HAS-A
	
}
