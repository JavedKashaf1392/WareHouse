package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.ShipmentType;

public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer>,JpaSpecificationExecutor<ShipmentType> {

	@Query("SELECT COUNT(ST.shipmentCode) FROM ShipmentType ST WHERE ST.shipmentCode=:code")
	public Integer getShipmentCodeCount(String code);
	
	 @Query("SELECT COUNT(ST.shipmentCode) FROM ShipmentType ST WHERE ST.shipmentCode=:code AND ST.id!=:id")
	 public Integer getShipmentCodeCountForEdit(String code,Integer id);

	@Query("SELECT ST.shipmentMode,count(ST.shipmentMode) FROM ShipmentType ST GROUP BY ST.shipmentMode")
	public List<Object[]> getShipmentModeCount();

	@Query("SELECT ST.id,ST.shipmentCode FROM ShipmentType ST WHERE ST.enableShipment='Yes'")
	public List<Object[]> getShipmentIdAndCode();
}
