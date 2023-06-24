package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import in.nit.model.Uom;

public interface IUomService {
	Integer saveUom(Uom u);

	void updateUom(Uom u);

	void deleteUom(Integer id);

	Optional<Uom> getOneUom(Integer id);

	List<Uom> getAllUoms();

	Page<Uom> getAllUoms(Pageable pageable);
	Page<Uom> findByName( String name, Pageable pageable);

	boolean isUomExist(Integer id);
	
	boolean isUomModelExist(String uomModel);

	List<Object[]> getUomTypeCount();

	Map<Integer, String> getUomByIdAndName();
	//Data Filtering //without pagination
	List<Uom> getAllUoms(Specification<Uom>spec);
	//Data Filtering with pagination
	Page<Uom> getAllUoms(Specification<Uom>spec,Pageable pageable);

}
