package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {
	
	//email checking
	@Query("SELECT COUNT(WH.userEmail) FROM WhUserType WH WHERE WH.userEmail=:mail")
	public Integer getWhUserTypeCount(String mail);
	
	//code counting check
	 @Query("SELECT COUNT(WH.userCode) FROM WhUserType WH WHERE WH.userCode=:code")
	 public Integer getWhUserTypeCodeCount(String code);
	
	 @Query("SELECT WH.userType,count(WH.userType) FROM WhUserType WH GROUP BY WH.userType")
	 public List<Object[]> getWhUserTypeCount();
	 
	 @Query("SELECT WH.id, WH.userCode FROM WhUserType WH WHERE WH.userType=:userType")
		public List<Object[]> getWhUserTypeIdAndCode(String userType);
}
