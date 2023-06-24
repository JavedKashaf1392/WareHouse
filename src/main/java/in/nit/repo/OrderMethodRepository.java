package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer>, JpaSpecificationExecutor<OrderMethod>{

	 @Query("SELECT OM.orderMode,count(OM.orderMode) FROM OrderMethod OM GROUP BY OM.orderMode")
	 public List<Object[]> getOrderModeCount();
}
