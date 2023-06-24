package in.nit.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.ShipmentType;
import in.nit.service.IShipmentTypeService;

@RestController
@RequestMapping("/rest/ShipmentType")
public class ShipmentTypeRestController {

	@Autowired
	private IShipmentTypeService service;

	// 1.Get all records from DB
	@GetMapping("/all")
	public ResponseEntity<List<ShipmentType>> getAll() {
		List<ShipmentType> list = service.getAllShipmentTypes();
		// return ResponseEntity.ok(list);

		return new ResponseEntity<List<ShipmentType>>(list, HttpStatus.OK);
	}

	// 2.Get one record from DB
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		Optional<ShipmentType> opt = service.getOneShipmentType(id);
		ResponseEntity<?> resp = null;
		if (opt.isPresent()) {
			resp = new ResponseEntity<ShipmentType>(opt.get(), HttpStatus.OK);
		} else {
			resp = new ResponseEntity<String>("Record Not Found", HttpStatus.NOT_FOUND);

		}
		return resp;
	}

	// 3.Delete one record from DB
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		if (service.isShipmentTypeExist(id)) {
			try {
				service.deleteShipmentType(id);
				resp = new ResponseEntity<String>("RECORD HAS BEEN DELETED WITH " + id, HttpStatus.OK);
			} catch (Exception e) {
				resp = new ResponseEntity<String>("RECORD CAN'T BE DELETED, IT USED ANOTHER OPERATIONS " + id,
						HttpStatus.BAD_REQUEST);
			}
		} else {
			resp = new ResponseEntity<String>("RECORD WITH " + id + " NOT FOUND", HttpStatus.NOT_FOUND);
		}
		return resp;
	}

	// 4.Save record into DB
	@PostMapping("/insert")
	public ResponseEntity<String> save(@RequestBody ShipmentType shipmentType) {
		ResponseEntity<String> resp = null;
		try {
			Integer id = service.saveShipmentType(shipmentType);
			resp = new ResponseEntity<String>("RECORD HAS BEEN SAVED WITH " + id, HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("RECORD CAN'T BE SAVED WITH " + shipmentType.getId(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// 5.Update record in DB
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody ShipmentType shipmentType) {
		ResponseEntity<String> resp = null;
		if (shipmentType.getId() == null || !service.isShipmentTypeExist(shipmentType.getId())) {
			resp = new ResponseEntity<String>("RECORD NOT EXIST IN DB", HttpStatus.BAD_REQUEST);
		} else {
			service.updateShipmentType(shipmentType);
			resp = new ResponseEntity<String>("ShipmentType WITH '" + shipmentType.getId() + "' UPDATED",
					HttpStatus.OK);
		}
		return resp;
	}

}
