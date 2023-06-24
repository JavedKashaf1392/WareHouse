package in.nit.validate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.nit.model.Uom;
import in.nit.service.IUomService;

@Component
public class UomValidator {


	@Autowired
	private IUomService service;
	
	public Map<String,String>validate(Uom uom){
		Map<String,String>errors= new HashMap<>();
		//Normal validation
		if(uom.getUomType()==null || uom.getUomType().isEmpty()) {
			errors.put("uomType", "Invaild Uom Type Entered");
		} 
		
		if(uom.getUomModel()==null || uom.getUomModel().isEmpty()) {
			errors.put("uomModel", "Invaild Uom Model Entered");
		} else if(service.isUomModelExist(uom.getUomModel())) {
			errors.put("uomModel", "Uom Model already Exist!");
			
		}
		
		return errors;
	}
}
