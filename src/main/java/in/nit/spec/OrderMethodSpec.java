package in.nit.spec;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import in.nit.model.OrderMethod;

//Generate dynamic SQL for filtering
public class OrderMethodSpec implements Specification<OrderMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderMethod filter;

	public OrderMethodSpec(OrderMethod filter) {
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(
			Root<OrderMethod> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder cb)
	{
		Predicate p = cb.conjunction();
		List<Expression<Boolean>> expression = p.getExpressions();

		if (filter.getOrderCode() != null && !filter.getOrderCode().isEmpty()) {
			expression.add(cb.like(
					root.get("orderCode"), 
					"%" + filter.getOrderCode() + "%"
					)
					);
		}
		if (filter.getOrderType() != null && !filter.getOrderType().isEmpty()) {
			expression.add(cb.like(
					root.get("orderType"), 
					"%" + filter.getOrderType() + "%"
					)
					);
		}
		return p;
	}

}
