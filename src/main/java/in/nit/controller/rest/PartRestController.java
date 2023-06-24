package in.nit.controller.rest;

import java.util.List;
import java.util.Map;
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

import in.nit.model.Part;
import in.nit.service.IPartService;
import in.nit.validate.PartValidator;

@RestController
@RequestMapping("/rest/Part")
public class PartRestController {

	@Autowired
	private IPartService service;
	@Autowired
	private PartValidator validator;

	// 1.Get all records from DB
	@GetMapping("/all")
	public ResponseEntity<List<Part>> getAll() {
		List<Part> list = service.getAllParts();
		// return ResponseEntity.ok(list);

		return new ResponseEntity<List<Part>>(list, HttpStatus.OK);
	}

	// 2.Get one record from DB
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id) {
		Optional<Part> opt = service.getOnePart(id);
		ResponseEntity<?> resp = null;
		if (opt.isPresent()) {
			resp = new ResponseEntity<Part>(opt.get(), HttpStatus.OK);
		} else {
			resp = new ResponseEntity<String>("Record Not Found", HttpStatus.NOT_FOUND);

		}
		return resp;
	}

	// 3.Delete one record from DB
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> resp = null;
		if (service.isPartExist(id)) {
			try {
				service.deletePart(id);
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
	@PostMapping("/save")
	public ResponseEntity<String> savePart(@RequestBody Part part) {
		ResponseEntity<String> resp = null;
		Map<String,String>errors=validator.validate(part);	;
		if(errors.isEmpty()) {
			Integer id = service.savePart(part);
			resp = new ResponseEntity<String>("RECORD HAS BEEN SAVED WITH " + id, HttpStatus.OK);
		}
		try {
		} catch (Exception e) {
			resp = new ResponseEntity<String>(errors+"",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// 5.Update record in DB
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Part part) {
		ResponseEntity<String> resp = null;
		if (part.getId() == null || !service.isPartExist(part.getId())) {
			resp = new ResponseEntity<String>("RECORD NOT EXIST IN DB", HttpStatus.BAD_REQUEST);
		} else {
			service.updatePart(part);
			resp = new ResponseEntity<String>("Part WITH '" + part.getId() + "' UPDATED",
					HttpStatus.OK);
		}
		return resp;
	}

}
