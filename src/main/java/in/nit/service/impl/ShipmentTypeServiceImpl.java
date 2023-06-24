package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.exception.custom.DataNotFoundException;
import in.nit.model.ShipmentType;
import in.nit.repo.ShipmentTypeRepository;
import in.nit.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

	@Autowired
	private ShipmentTypeRepository repo;

	@Transactional
	public Integer saveShipmentType(ShipmentType st) {
		Integer id = repo.save(st).getId();
		return id;
	}

	@Transactional
	public void updateShipmentType(ShipmentType st) {
		Optional<ShipmentType> opt = repo.findById(st.getId());
		if (!opt.isPresent())
			throw new DataNotFoundException("Shipment Type '" + st.getId() + "' Not Found");

		repo.save(st);
	}

	@Transactional
	public void deleteShipmentType(Integer id) {
		Optional<ShipmentType> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new DataNotFoundException("Shipment Type '" + id + "' Not Found");

		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<ShipmentType> getOneShipmentType(Integer id) {
		Optional<ShipmentType> opt = repo.findById(id);
		if (!opt.isPresent())
			throw new DataNotFoundException("Shipment Type '" + id + "' Not Found");
		return opt;
	}

	@Transactional(readOnly = true)
	public List<ShipmentType> getAllShipmentTypes() {
		List<ShipmentType> list = repo.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	public boolean isShipmentTypeExist(Integer id) {
		boolean exist = repo.existsById(id);
		return exist;
	}

	@Transactional(readOnly = true)
	public boolean isShipmentTypeCodeExist(String shipmentCode) {
		int count = repo.getShipmentCodeCount(shipmentCode);
		boolean flag = (count > 0 ? true : false);
		return flag;
	}
	@Override
	public boolean isShipmentTypeCodeExistForEdit(String code, Integer id) {
		return repo.getShipmentCodeCountForEdit(code, id)>0?true:false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getShipmentModeCount() {
		return repo.getShipmentModeCount();
	}
	@Override
	@Transactional(readOnly = true)
	public Map<Integer, String> getShipmentIdAndCode() {
		return  repo.getShipmentIdAndCode().stream()
		.collect(Collectors.
				toMap(ob->Integer.valueOf(ob[0].toString()),
						ob->ob[1].toString()));	
	}
	
	@Override
	public List<ShipmentType> getAllShipmentTypes(Specification<ShipmentType> spec) {
		return repo.findAll(spec);
	}
}
