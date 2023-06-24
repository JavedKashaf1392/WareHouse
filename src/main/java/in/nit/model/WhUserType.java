package in.nit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="wh_user_type_tab")
public class WhUserType {

	@Id
	@GeneratedValue(generator ="wh_gen")
	@SequenceGenerator(name = "wh_gen", sequenceName = "wh_gen_seq")
	
	@Column(name="wh_id_col")
	private Integer id;
	
	@Column(name="wh_type_col")
	private String userType;
	
	@Column(name="wh_code_col")
	private String userCode;
	
	@Column(name="wh_userfor_col")
	private String userFor;
	
	@Column(name="wh_email_col")
	private String userEmail;
	
	@Column(name="wh_contact_col")
	private String userContact;
	
	@Column(name="wh_idtype_col")
	private String userIdType;
	
	@Column(name="wh_usr_if_other_col")
	private String ifother;
	
	@Column(name="wh_usr_if_num_col")
	private String idNumber;
	
}
