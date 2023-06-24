package in.nit.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.Uom;
import in.nit.service.IUomService;

@RestController
@RequestMapping("/rest/uom")
public class UomRestController {

	@Autowired
	private IUomService service;
	// 1.Get all records from DB

	@GetMapping("/all")
	public ResponseEntity<List<Uom>> getAll() {
		List<Uom> list = service.getAllUoms();
		// return ResponseEntity.ok(list);

		return new ResponseEntity<List<Uom>>(list, HttpStatus.OK);
	}

	// 2.Get one record from DB
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		Optional<Uom> opt = service.getOneUom(id);
		ResponseEntity<?> resp = null;
		if (opt.isPresent()) {
			resp = new ResponseEntity<Uom>(opt.get(), HttpStatus.OK);
		} else {
			resp = new ResponseEntity<String>("Record Not Found", HttpStatus.NOT_FOUND);

		}
		return resp;
	}

	// 3.Delete one record from DB
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		if (service.isUomExist(id)) {
			try {
				service.deleteUom(id);
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
	public ResponseEntity<?> save(@Valid @RequestBody Uom uom, BindingResult errors) {
		ResponseEntity<?> resp = null;
		try {
			if (errors.hasErrors()) {
				//if error happens
				HashMap<String,String>errorsMap= new HashMap();
				for(FieldError err:errors.getFieldErrors()) {
					errorsMap.put(err.getField(), err.getDefaultMessage());
				}
				resp = new ResponseEntity<HashMap<String,String>>(errorsMap,HttpStatus.BAD_REQUEST);
			} else {
				//save data
				Integer id = service.saveUom(uom);
				resp = new ResponseEntity<String>("RECORD HAS BEEN SAVED WITH " + id, HttpStatus.OK);
			}
		} catch (Exception e) {
			resp = new ResponseEntity<String>("RECORD CAN'T BE SAVED WITH " + uom.getId(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// 5.Update record in DB
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Uom uom) {
		ResponseEntity<String> resp = null;
		if (uom.getId() == null || !service.isUomExist(uom.getId())) {
			resp = new ResponseEntity<String>("RECORD NOT EXIST IN DB", HttpStatus.BAD_REQUEST);
		} else {
			service.updateUom(uom);
			resp = new ResponseEntity<String>("UOM WITH '" + uom.getId() + "' UPDATED", HttpStatus.OK);
		}
		return resp;
	}

}
