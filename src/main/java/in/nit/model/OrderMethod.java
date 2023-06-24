package in.nit.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name ="order_method_tab")
public class OrderMethod {
	@Id
	@GeneratedValue(generator = "om_gen")
	@SequenceGenerator(name = "om_gen",sequenceName = "om_gen_seq")
	
	@Column(name="ord_id_col",length = 10, nullable = false)
	private Integer id;

	@Column(name="ord_mode_col",length = 15, nullable = false)
	private String orderMode;
	
	@Column(name="ord_code_col",length = 15, nullable = false)
	private String orderCode;
	
	@Column(name="ord_type_col",length = 15, nullable = false)
	private String orderType;
	
	@ElementCollection
	@CollectionTable(name="ord_acpt_tab", joinColumns = @JoinColumn(name="ord_id_col",nullable = false))
	@Column(name="ord_acpt_col",length = 20, nullable = false)
	List<String>orderAcpt;
	
	@Column(name="ord_desc_col",length = 150, nullable = false)
	private String description;
	
}
