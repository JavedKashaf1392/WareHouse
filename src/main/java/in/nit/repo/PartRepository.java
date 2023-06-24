package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.Part;

public interface PartRepository extends JpaRepository<Part, Integer> {
	@Query("SELECT COUNT(P.partCode) FROM Part P WHERE P.partCode=:code")
	public Integer getPartCodeCount(String code);

	@Query("SELECT P.id,P.partCode FROM Part P")
	public List<Object[]> getPartIdAndCode();

	@Query("SELECT COUNT(P.partCode) FROM Part P WHERE P.partCode=:code AND P.id!=:id")
	public Integer getPartCodeCountForEdit(String code, Integer id);

	@Query("SELECT P.baseCurr,count(P.baseCurr) FROM Part P GROUP BY P.baseCurr")
	public List<Object[]> getBaseCurrCount();
}
