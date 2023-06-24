package in.nit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.exception.custom.DataNotFoundException;
import in.nit.model.OrderMethod;
import in.nit.repo.OrderMethodRepository;
import in.nit.service.IOrderMethodService;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

	@Autowired
	private OrderMethodRepository repo;

	@Override
	@Transactional
	public Integer saveOrderMethod(OrderMethod om) {
		return repo.save(om).getId();
	}

	@Override
	@Transactional
	public void updateOrderMethod(OrderMethod om) {
		Optional<OrderMethod> opt = repo.findById(om.getId());
		if (!opt.isPresent())
			throw new DataNotFoundException("Order Method '" + om.getId() + "' Not Found");
		repo.save(om);
	}

	@Override
	@Transactional
	public void deleteOrderMethod(Integer id) {
		Optional<OrderMethod> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new DataNotFoundException("Order Method '" + id + "' Not Found");

		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<OrderMethod> getOneOrderMethod(Integer id) {
		Optional<OrderMethod> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new DataNotFoundException("Order Method '" + id + "' Not Found");

		return opt;
	}	

	@Override
	@Transactional(readOnly = true)
	public List<OrderMethod> getAllOrderMethods() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isOrderMethodExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public List<Object[]> getOrderModeCount() {
		return repo.getOrderModeCount();
	}
	@Override
	public List<OrderMethod> getAllOrderMethods(Specification<OrderMethod> spec) {
		return repo.findAll(spec);
	}
}
