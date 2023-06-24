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

import in.nit.model.OrderMethod;
import in.nit.service.IOrderMethodService;

@RestController
@RequestMapping("/rest/ordermethod")
public class OrderMethodRestController {

	@Autowired
	private IOrderMethodService service;
	@GetMapping("/index")
	public String index() {
		String page=null;
		page= " <h1>"
				+ "Welcome to RestController"
				+ "</h1>"
				+ "<h2><b>"
				+ "It uses @RestController Annotation"
				+ "</b></h2>";
				
				
		return page;
	}
	// 1.Get all records from DB
	@GetMapping("/all")
	public ResponseEntity<List<OrderMethod>> getAll() {
		List<OrderMethod> list = service.getAllOrderMethods();
		// return ResponseEntity.ok(list);

		return new ResponseEntity<List<OrderMethod>>(list, HttpStatus.OK);
	}

	// 2.Get one record from DB
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		Optional<OrderMethod> opt = service.getOneOrderMethod(id);
		ResponseEntity<?> resp = null;
		if (opt.isPresent()) {
			resp = new ResponseEntity<OrderMethod>(opt.get(), HttpStatus.OK);
		} else {
			resp = new ResponseEntity<String>("Record Not Found", HttpStatus.NOT_FOUND);

		}
		return resp;
	}

	// 3.Delete one record from DB
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		if (service.isOrderMethodExist(id)) {
			try {
				service.deleteOrderMethod(id);
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
	public ResponseEntity<String> save(@RequestBody OrderMethod orderMethod) {
		ResponseEntity<String> resp = null;
		try {
			Integer id = service.saveOrderMethod(orderMethod);
			resp = new ResponseEntity<String>("RECORD HAS BEEN SAVED WITH " + id, HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("RECORD CAN'T BE SAVED WITH " + orderMethod.getId(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// 5.Update record in DB
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody OrderMethod orderMethod) {
		ResponseEntity<String> resp = null;
		if (orderMethod.getId() == null || !service.isOrderMethodExist(orderMethod.getId())) {
			resp = new ResponseEntity<String>("RECORD NOT EXIST IN DB", HttpStatus.BAD_REQUEST);
		} else {
			service.updateOrderMethod(orderMethod);
			resp = new ResponseEntity<String>("OrderMethod WITH '" + orderMethod.getId() + "' UPDATED", HttpStatus.OK);
		}
		return resp;
	}

}
