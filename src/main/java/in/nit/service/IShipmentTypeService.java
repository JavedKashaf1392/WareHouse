package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import in.nit.model.ShipmentType;

public interface IShipmentTypeService {

	Integer saveShipmentType(ShipmentType st);

	void updateShipmentType(ShipmentType st);

	void deleteShipmentType(Integer id);

	Optional<ShipmentType> getOneShipmentType(Integer id);

	List<ShipmentType> getAllShipmentTypes();

	boolean isShipmentTypeExist(Integer id);

	boolean isShipmentTypeCodeExist(String shipmentCode);

	boolean isShipmentTypeCodeExistForEdit(String code, Integer id);

	List<Object[]> getShipmentModeCount();

	Map<Integer, String> getShipmentIdAndCode();

	List<ShipmentType> getAllShipmentTypes(Specification<ShipmentType> spec);
}
