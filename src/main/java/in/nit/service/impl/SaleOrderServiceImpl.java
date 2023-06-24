package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.exception.custom.DataNotFoundException;
import in.nit.model.SaleOrder;
import in.nit.model.SaleDtl;
import in.nit.repo.SaleDtlRepo;
import in.nit.repo.SaleOrderRepository;
import in.nit.service.ISaleOrderService;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

	@Autowired
	private SaleOrderRepository repo;
	@Autowired
	private SaleDtlRepo dtlRepo;

	@Override
	@Transactional()
	public Integer saveSaleOrder(SaleOrder order) {
		return repo.save(order).getId();
	}

	@Override
	@Transactional()
	public void updateSaleOrder(SaleOrder order) {
		Optional<SaleOrder>opt= repo.findById(order.getId());
		if(!opt.isPresent())
			throw new DataNotFoundException("Sale Order '"+order.getId()+"'Not Found");
		repo.save(order);
	}

	@Override
	@Transactional()
	public void deleteSaleOrder(Integer id) {
		Optional<SaleOrder> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new DataNotFoundException("Sale Order '" + id + "'Not Found");
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SaleOrder> getOneSaleOrder(Integer id) {
		Optional<SaleOrder> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new DataNotFoundException("Sale Order '" + id + "'Not Found");
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SaleOrder> getAllSaleOrders() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isSaleOrderExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isSaleOrderCodeExist(String orderCode) {
		return repo.getOrderCodeCount(orderCode) > 0 ? true : false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getStockModeCount() {
		return repo.getstockModeCount();
	}

	/**
	 * Screen#2 Service
	 */
	@Override
	@Transactional
	public Integer addPartToSo(SaleDtl dtl) {
		return dtlRepo.save(dtl).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<SaleDtl> getSaleDtlWithSoId(Integer saleId) {
		return dtlRepo.getSaleDtlWithSoId(saleId);
	}

	@Override
	@Transactional
	public void deleteSaleDtl(Integer id) {
		dtlRepo.deleteById(id);
	}

	@Override
	@Transactional
	public void updateSaleOrderStatus(String status, Integer id) {
		repo.updateSaleOrderStatus(status, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getSaleDtlCountWithSoId(Integer saleId) {
		return dtlRepo.getSaleDtlCountWithSoId(saleId);
	}

	@Override
	public Map<Integer, String> getSoIdAndCodeByStatus(String status) {
		return repo.getSoIdAndCodeByStatus(status).stream()
				.collect(Collectors.toMap(ob -> Integer.valueOf(ob[0].toString()), ob -> ob[1].toString()));
	}
}
