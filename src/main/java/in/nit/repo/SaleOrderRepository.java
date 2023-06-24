package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.SaleOrder;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {

	@Query("SELECT COUNT(SO.orderCode) FROM SaleOrder SO WHERE SO.orderCode=:code")
	public Integer getOrderCodeCount(String code);

	@Query("SELECT SO.stockMode,count(SO.stockMode) FROM SaleOrder SO GROUP BY SO.stockMode")
	public List<Object[]> getstockModeCount();

	/**
	 * 
	 * Screen #2 Operations -----------------//
	 **/

	@Modifying
	@Query("UPDATE SaleOrder SET status=:status WHERE id=:id")
	public void updateSaleOrderStatus(String status, Integer id);
	
	@Query("SELECT SO.id,SO.orderCode FROM SaleOrder SO WHERE SO.status=:status")
	public List<Object[]> getSoIdAndCodeByStatus(String status);
}
