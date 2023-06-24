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

import in.nit.model.SaleOrder;
import in.nit.service.ISaleOrderService;

@RestController
@RequestMapping("/rest/saleorder")
public class SaleOrderRestController {

	@Autowired
	private ISaleOrderService service;

	// 1.Get all records from DB
	@GetMapping("/all")
	public ResponseEntity<List<SaleOrder>> getAll() {
		List<SaleOrder> list = service.getAllSaleOrders();
		// return ResponseEntity.ok(list);

		return new ResponseEntity<List<SaleOrder>>(list, HttpStatus.OK);
	}

	// 2.Get one record from DB
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		Optional<SaleOrder> opt = service.getOneSaleOrder(id);
		ResponseEntity<?> resp = null;
		if (opt.isPresent()) {
			resp = new ResponseEntity<SaleOrder>(opt.get(), HttpStatus.OK);
		} else {
			resp = new ResponseEntity<String>("Record Not Found", HttpStatus.NOT_FOUND);

		}
		return resp;
	}

	// 3.Delete one record from DB
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		if (service.isSaleOrderExist(id)) {
			try {
				service.deleteSaleOrder(id);
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
	public ResponseEntity<String> save(@RequestBody SaleOrder saleOrder) {
		ResponseEntity<String> resp = null;
		try {
			Integer id = service.saveSaleOrder(saleOrder);
			resp = new ResponseEntity<String>("RECORD HAS BEEN SAVED WITH " + id, HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("RECORD CAN'T BE SAVED WITH " + saleOrder.getId(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// 5.Update record in DB
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody SaleOrder saleOrder) {
		ResponseEntity<String> resp = null;
		if (saleOrder.getId() == null || !service.isSaleOrderExist(saleOrder.getId())) {
			resp = new ResponseEntity<String>("RECORD NOT EXIST IN DB", HttpStatus.BAD_REQUEST);
		} else {
			service.updateSaleOrder(saleOrder);
			resp = new ResponseEntity<String>("SaleOrder WITH '" + saleOrder.getId() + "' UPDATED",
					HttpStatus.OK);
		}
		return resp;
	}

}
