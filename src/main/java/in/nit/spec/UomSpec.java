package in.nit.spec;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import in.nit.model.Uom;

//Generate dynamic SQL for filtering
public class UomSpec implements Specification<Uom> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Uom filter;

	public UomSpec(Uom filter) {
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(
			Root<Uom> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder cb)
	{
		Predicate p = cb.conjunction();
		List<Expression<Boolean>> expression = p.getExpressions();

		if (filter.getUomModel() != null && !filter.getUomModel().isEmpty()) {
			expression.add(cb.like(
					root.get("uomModel"), 
					"%" + filter.getUomModel() + "%"
					)
					);
		}
		if (filter.getUomType() != null && !filter.getUomType().isEmpty()) {
			expression.add(cb.like(
					root.get("uomType"), 
					"%" + filter.getUomType() + "%"
					)
					);
		}
		return p;
	}

}
