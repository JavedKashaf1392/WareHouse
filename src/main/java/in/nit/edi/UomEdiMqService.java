package in.nit.edi;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.nit.model.Uom;
import in.nit.service.IUomService;
import in.nit.util.EmailUtil;
import in.nit.validate.UomValidator;

//@Component
public class UomEdiMqService {
	private Logger logger = LoggerFactory.getLogger(UomEdiMqService.class);

	@Autowired
	private IUomService service;
	@Autowired
	private EmailUtil util;
	@Autowired
	private UomValidator validator;

//	@Value("${myapp.admin.email}")
	private String to;

	@JmsListener(destination = "uomCreate")
	public void createUom(String uomJson) {
		String message = null;
		try {
			logger.info("UOM EDI SERVICE-SAVE OPERATION");
			ObjectMapper om = new ObjectMapper();
			Uom ob = om.readValue(uomJson, Uom.class);
			Map<String,String>errors= validator.validate(ob);
			if(errors.isEmpty()) {
				Integer id = service.saveUom(ob);
				message = "UOM EDI SAVE SUCCESSFULLY WITH ID :" + id;
				logger.info(message);
			}else {
				message = "UOM EDI FAIL TO SAVE  WITH ERRORS-> :" + errors.values().toString();
				logger.info(message);
				
			}
		} catch (Exception e) {
			message = "UOM FAIL :" + e.getMessage();
			logger.error(message);
			e.printStackTrace();
		}
		util.send(to, "CREATE-UOM EDI SERVICE", message);
	}
}
