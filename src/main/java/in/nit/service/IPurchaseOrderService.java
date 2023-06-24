package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;

public interface IPurchaseOrderService {

	Integer savePurchaseOrder(PurchaseOrder order);

	void updatePurchaseOrder(PurchaseOrder order);

	void deletePurchaseOrder(Integer id);

	Optional<PurchaseOrder> getOnePurchaseOrder(Integer id);

	List<PurchaseOrder> getAllPurchaseOrders();

	boolean isPurchaseOrderExist(Integer id);

	boolean isPurchaseOrderCodeExist(String orderCode);
	
	boolean isPurchaseOrderCodeExistForEdit(String code,Integer id);

	List<Object[]> getPurchaseOrderQualityCheckCount();
	Page<PurchaseOrder> getAllPurchaseOrders(Pageable pageable);
	Page<PurchaseOrder> findByCode( String code, Pageable pageable);

	/**
	 * Screen#2 operations
	 */
	public Integer addPartToPo(PurchaseDtl dtl);

	List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId);

	void deletePurchaseDtl(Integer id);

	void updatePurchaseOrderStatus(String status, Integer id);
	
	Integer getPurchaseDtlCountWithPoId(Integer purchaseId);
	
	Map<Integer,String> getPoIdAndCodeByStatus(String status);

}
