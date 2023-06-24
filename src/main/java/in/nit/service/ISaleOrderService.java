package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.SaleDtl;
import in.nit.model.SaleOrder;

public interface ISaleOrderService {

	Integer saveSaleOrder(SaleOrder order);

	void updateSaleOrder(SaleOrder order);

	void deleteSaleOrder(Integer id);

	Optional<SaleOrder> getOneSaleOrder(Integer id);

	List<SaleOrder> getAllSaleOrders();

	boolean isSaleOrderExist(Integer id);

	boolean isSaleOrderCodeExist(String orderCode);

	List<Object[]> getStockModeCount();

	/**
	 * Screen#2 operations
	 */
	public Integer addPartToSo(SaleDtl dtl);

	List<SaleDtl> getSaleDtlWithSoId(Integer saleId);

	void deleteSaleDtl(Integer id);

	void updateSaleOrderStatus(String status, Integer id);

	Integer getSaleDtlCountWithSoId(Integer saleId);
	
	Map<Integer,String> getSoIdAndCodeByStatus(String status);
}
