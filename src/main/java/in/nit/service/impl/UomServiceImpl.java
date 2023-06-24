package in.nit.service.impl;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.Uom;
import in.nit.repo.UomRepository;
import in.nit.service.IUomService;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repo;

	@Transactional
	public Integer saveUom(Uom u) {
		Integer id = repo.save(u).getId();
		return id;
	}

	@Transactional
	public void updateUom(Uom u) {
		repo.save(u);
	}

	@Transactional
	public void deleteUom(Integer id) {
		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<Uom> getOneUom(Integer id) {
		Optional<Uom> opt = repo.findById(id);
		return opt;
	}

	@Transactional(readOnly = true)
	public List<Uom> getAllUoms() {
		List<Uom> list = repo.findAll();
		return list;
	}
	@Override
	@Transactional(readOnly = true)
	public Page<Uom> findByName(String name, Pageable pageable) {
		return repo.findByName(name, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Uom> getAllUoms(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public boolean isUomExist(Integer id) {
		boolean exist = repo.existsById(id);
		return exist;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getUomTypeCount() {
		return repo.getUomTypeCount();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Integer, String> getUomByIdAndName() {
		/*
		 * List<Object[]>list=repo.getUomByIdAndName(); Map<Integer,String>map=new
		 * LinkedHashMap<>(); for(Object[] ob:list) {
		 * map.put(Integer.valueOf(ob[0].toString()), ob[1].toString()); }
		 */
		Map<Integer, String> map = repo.getUomByIdAndName().stream()
				/* .filter(array->array[0]!=null) */
				.collect(Collectors.toMap(array -> Integer.valueOf(array[0].toString()), array -> array[1].toString()));
		return map;
	}
	@Override
	public List<Uom> getAllUoms(Specification<Uom> spec) {
		return repo.findAll(spec);
	}
	@Override
	public Page<Uom> getAllUoms( Specification<Uom> spec,Pageable pageable) {
		return repo.findAll(spec, pageable);
	}
	
	@Override
	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel)>0?true:false;
	}
}
