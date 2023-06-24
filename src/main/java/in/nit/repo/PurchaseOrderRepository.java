package in.nit.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

	@Query("SELECT COUNT(PO.orderCode) FROM PurchaseOrder PO WHERE PO.orderCode=:code")
	public Integer getPurchaseOrderCodeCount(String code);

	@Query("SELECT COUNT(PO.orderCode) FROM PurchaseOrder PO WHERE PO.orderCode=:code AND PO.id!=:id")
	public Integer getShipmentCodeCountForEdit(String code, Integer id);

	@Query("SELECT PO.qualityCheck,count(PO.qualityCheck) FROM PurchaseOrder PO GROUP BY PO.qualityCheck")
	public List<Object[]> getPurchaseOrderQualityCheckCount();

	 @Query("SELECT PO FROM PurchaseOrder PO WHERE PO.defaultStatus like %?1%")
	 public Page<PurchaseOrder> findByCode( String code, Pageable pageable);
	
	/**
	 * Screen#2 Operations
	 */

	@Modifying
	@Query("UPDATE PurchaseOrder SET defaultStatus=:status WHERE id=:id")
	public void updatePurchaseOrderStatus(String status, Integer id);
	
	@Query("SELECT PO.id,PO.orderCode FROM PurchaseOrder PO WHERE PO.defaultStatus=:status")
	public List<Object[]> getPoIdAndCodeByStatus(String status);
	
	

}
