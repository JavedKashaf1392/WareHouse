package in.nit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import in.nit.model.OrderMethod;

public interface IOrderMethodService {

	Integer saveOrderMethod(OrderMethod om);

	void updateOrderMethod(OrderMethod om);

	void deleteOrderMethod(Integer id);

	Optional<OrderMethod> getOneOrderMethod(Integer id);

	List<OrderMethod> getAllOrderMethods();

	boolean isOrderMethodExist(Integer id);

	List<Object[]> getOrderModeCount();
	List<OrderMethod> getAllOrderMethods(Specification<OrderMethod> spec);
}
