package in.nit.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.nit.model.Uom;
@Repository
public interface UomRepository extends JpaRepository<Uom, Integer>,JpaSpecificationExecutor<Uom> {

	 @Query("SELECT U.uomType,count(U.uomType) FROM Uom U GROUP BY U.uomType")
	 public List<Object[]> getUomTypeCount();	
	 
	 @Query("SELECT U.id,U.uomModel FROM Uom U")
	 public List<Object[]>getUomByIdAndName();
	 

	 @Query("SELECT COUNT(U.uomModel) FROM Uom U WHERE U.uomModel=:uomModel")
		public Integer getUomModelCount(String uomModel);
	 
	 @Query("SELECT U FROM Uom U WHERE U.uomType like %?1%")
	 public Page<Uom> findByName( String name, Pageable pageable);
}
