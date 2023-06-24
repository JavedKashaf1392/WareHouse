package in.nit.spec;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import in.nit.model.ShipmentType;

//Generate dynamic SQL for filtering
public class ShipmentTypeSpec implements Specification<ShipmentType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShipmentType filter;

	public ShipmentTypeSpec(ShipmentType filter) {
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(
			Root<ShipmentType> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder cb)
	{
		Predicate p = cb.conjunction();
		List<Expression<Boolean>> expression = p.getExpressions();

		if (filter.getShipmentCode() != null && !filter.getShipmentCode().isEmpty()) {
			expression.add(cb.like(
					root.get("shipmentCode"), 
					"%" + filter.getShipmentCode() + "%"
					)
					);
		}
		if (filter.getShipmentMode() != null && !filter.getShipmentMode().isEmpty()) {
			expression.add(cb.like(
					root.get("shipmentMode"), 
					"%" + filter.getShipmentMode() + "%"
					)
					);
		}
		return p;
	}

}
