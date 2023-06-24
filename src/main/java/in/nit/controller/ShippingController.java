package in.nit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nit.model.SaleDtl;
import in.nit.model.Shipping;
import in.nit.model.ShippingDtl;
import in.nit.service.ISaleOrderService;
import in.nit.service.IShippingService;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
	@Autowired
	private IShippingService service;
	@Autowired
	private ISaleOrderService soservice;

	private void addDorpDownUi(Model model) {
		model.addAttribute("sos", soservice.getSoIdAndCodeByStatus("SALE-INVOICED"));
	}

	// 1. show ShippingReg page
	@GetMapping("/register")
	public String showReg(Model model) {
		model.addAttribute("shipping", new Shipping());
		addDorpDownUi(model);
		return "ShippingRegister";
	}

	// 2. save Shipping
	@PostMapping("/save")
	public String saveShipping(@ModelAttribute Shipping shipping, Model model) {
		Integer id = service.saveShipping(shipping);
		model.addAttribute("message", "SHIPPING SAVED WITH ID:" + id);
		model.addAttribute("shipping", new Shipping());
		if (id != null) {
			Integer soId = shipping.getSo().getId();
			// SO is used in Shipping. So, it is received.
			soservice.updateSaleOrderStatus("SALE-SHIPPED", soId);
			// map SaleDtls as ShippingDtl object
			convertSaleDtlToShippinDtl(id, soId);
		}
		addDorpDownUi(model);
		return "ShippingRegister";
	}

	// 3. fetch data to UI
	@GetMapping("/all")
	public String showData(Model model) {
		model.addAttribute("list", service.getAllShippings());
		return "ShippingData";
	}

	// code for Scrren#2
	private void convertSaleDtlToShippinDtl(Integer shippingId, Integer poId) {
		// 1. Get SaleDtls List using SoId
		List<SaleDtl> soDtlsList = soservice.getSaleDtlWithSoId(poId);

		// 2. convert one SoDtl object to one ShippingDtlObject
		for (SaleDtl soDtl : soDtlsList) {
			ShippingDtl shippingDtl = new ShippingDtl();
			shippingDtl.setItemCode(soDtl.getPart().getPartCode());

			shippingDtl.setBaseCost(soDtl.getPart().getBaseCost());
			shippingDtl.setQty(soDtl.getQty());
			shippingDtl.setItemValue(shippingDtl.getQty() * shippingDtl.getBaseCost());

			// link with shippingDtl with Shipping object (Parent)
			Shipping shipping = new Shipping();
			shipping.setId(shippingId); // only ID is OK.
			shippingDtl.setShipping(shipping);

			// save in Database
			service.saveShippingDtl(shippingDtl);

		}
	}

	// Display all Shipping Dtls here
	@GetMapping("/viewDtls/{id}")
	public String showDtls(@PathVariable Integer id, // SHIPPING ID
			Model model) {
		List<ShippingDtl> dtls = service.getAllDtlsByShippingId(id);
		model.addAttribute("dtls", dtls);
		return "ShippingDtlView";
	}
	
	//Change status for ACCEPTED and REJECTED
	@GetMapping("/status")
	public String updateDtlStatus(
			@RequestParam Integer shippingId,
			@RequestParam Integer dtlId,
			@RequestParam String status
			) 
	{
		service.updateStatusByShippinDtlId(status, dtlId);
		return "redirect:viewDtls/"+shippingId;
	}
}
