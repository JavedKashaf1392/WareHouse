package in.nit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;
import in.nit.service.IPartService;
import in.nit.service.IPurchaseOrderService;
import in.nit.service.IShipmentTypeService;
import in.nit.service.IWhUserTypeService;
import in.nit.util.PurchaseOrderUtil;
import in.nit.view.PurchaseOrderExcelView;
import in.nit.view.PurchaseOrderPdfView;
import in.nit.view.VendorInvoicePdf;

@Controller
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

	private Logger logger = LoggerFactory.getLogger(OrderMethodController.class);
	@Autowired
	private IPurchaseOrderService service;
	@Autowired
	private IShipmentTypeService shipmentService;
	@Autowired
	private IWhUserTypeService whUserTypeService;
	@Autowired
	private IPartService partService;

	@Autowired
	private ServletContext context;

	@Autowired
	private PurchaseOrderUtil util;

	// create method to get integration
	private void addDorpDownUi(Model model) {
		model.addAttribute("shipmentTypes", shipmentService.getShipmentIdAndCode());
		model.addAttribute("whUserTypes", whUserTypeService.getWhUserTypeIdAndCode("Vendor"));
	}
	// 1. Show Register Page

	@GetMapping("/register")
	public String showRegister(Model model) {

		model.addAttribute("purchaseOrder", new PurchaseOrder());
		addDorpDownUi(model);
		return "PurchaseOrderRegister";
	}

	// 2. save : on click submit

	@PostMapping("/save")
	public String save(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {

		Integer id = null;
		String message = null;
		// peform save operation
		id = service.savePurchaseOrder(purchaseOrder);
		// construct one message
		message = "purchase Order '" + id + "' saved successfully";
		// send message to UI
		model.addAttribute("message", message);
		addDorpDownUi(model);
		model.addAttribute("purchaseOrder", new PurchaseOrder());
		// got to Page
		return "PurchaseOrderRegister";
	}

	// 3.Displaying data:
	@GetMapping("/all")
	public String fetchAll(Model model, @PageableDefault(page = 0, size = 5) Pageable pageable,
			@RequestParam(defaultValue = "") String code) {
		if (code != null) {
			model.addAttribute("page", service.findByCode(code, pageable));
		} else {
			// model.addAttribute("list", service.getAllUoms());
			model.addAttribute("page", service.getAllPurchaseOrders(pageable));
		}
		// List<PurchaseOrder> list = service.getAllPurchaseOrders();
		// model.addAttribute("list", list);
		return "PurchaseOrderData";
	}

	// 4.delete record
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model,
			@PageableDefault(page = 0, size = 5) Pageable pageable) {
		String msg = null;
		// invoke service
		if (service.isPurchaseOrderExist(id)) {
			service.deletePurchaseOrder(id);
			msg = "Purchase Order '" + id + "' Type deleted !";
		} else {

			msg = "Purchase Order'" + id + "' Not Existed !";
		}
		// display other records
		/*
		 * List<PurchaseOrder> list = service.getAllPurchaseOrders();
		 * model.addAttribute("list", list);
		 */
		model.addAttribute("page", service.getAllPurchaseOrders(pageable));

		// send confirmation to UI
		model.addAttribute("message", msg);
		return "PurchaseOrderData";
	}

	// 5.Edit form
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			PurchaseOrder order = opt.get();
			model.addAttribute("purchaseOrder", order);
			addDorpDownUi(model);
			page = "PurchaseOrderEdit";
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 6.update method
	@PostMapping("/update")
	public String update(@ModelAttribute PurchaseOrder purchaseOrder, Model model,
			@PageableDefault(page = 0, size = 5) Pageable pageable) {
		String msg = null;
		try {
			logger.info("Calling Service");
			service.updatePurchaseOrder(purchaseOrder);
			logger.info("Constructing message");
			msg = "Purchase Order '" + purchaseOrder.getId() + "' updated successfully..";
			logger.info("Sending confirmation to client");
			model.addAttribute("message", msg);
			
			// display other records
			/*
			 * List<PurchaseOrder> list = service.getAllPurchaseOrders();
			 * model.addAttribute("list", list);
			 */
			logger.info("Using pageable list to present data");
			model.addAttribute("page", service.getAllPurchaseOrders(pageable));
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "PurchaseOrderData";
	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page = null;
		try {
			logger.info("Calling service");
			Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
			logger.info("Reading data from Object");
			PurchaseOrder order = opt.get();
			logger.info("Send data to UI");
			model.addAttribute("ob", order);
			page = "PurchaseOrderView";
			
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return page;
	}

	// 8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new PurchaseOrderExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllPurchaseOrders());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderExcelView());
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 10. Export Data to Pdf File
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new PurchaseOrderPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllPurchaseOrders());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderPdfView());
		Optional<PurchaseOrder> opt = service.getOnePurchaseOrder(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 12. ---AJAX VALIDATION----------
	@GetMapping("/validatecode")
	public @ResponseBody String validateShipmentCode(@RequestParam String code, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isPurchaseOrderCodeExist(code)) {
			message = "Shipment Code <b>'" + code + "' Already exist</b>!";
		} else if (service.isPurchaseOrderCodeExistForEdit(code, id)) {
			message = "Shipment Code <b>'" + code + "' Already exist</b>!";
		}
		return message;
	}

	// 12. Generate Chart

	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getPurchaseOrderQualityCheckCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "PurchaseOrderCharts";
	}

	/// *************************************************************************//
	// ** SCREEN#2 OPERATIONS ***//
	// *************************************************************************//

	// add below method
	private void addDorpDownUiForDtls(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	@GetMapping("/dtls/{id}")
	public String showDtls(@PathVariable Integer id, // PO Id,
			Model model) {
		String page = null;
		// get PO using id
		PurchaseDtl purchaseDtl = new PurchaseDtl();
		Optional<PurchaseOrder> po = service.getOnePurchaseOrder(id);
		if (po.isPresent()) {
			model.addAttribute("po", po.get());
			// To add parts
			addDorpDownUiForDtls(model);

			// Set PO id
			purchaseDtl.setPo(po.get());

			model.addAttribute("purchaseDtl", purchaseDtl);
			model.addAttribute("dtlList", service.getPurchaseDtlWithPoId(po.get().getId()));
			page = "PurchaseDtls";

		} else {
			page = "redirect:../all";
		}

		return page;
	}

	// 2. on click add button
	/**
	 * Read PurchaseDtl object and save DB redirect to /dtls/{id} -> showDtl()
	 * method
	 */
	@PostMapping("/addPart")
	public String addPartToPo(@ModelAttribute PurchaseDtl purchaseDtl) {
		service.addPartToPo(purchaseDtl);
		Integer poId = purchaseDtl.getPo().getId();
		service.updatePurchaseOrderStatus("PICKING", poId);
		return "redirect:dtls/" + poId; // POID
	}

	// 3 on click delete remove one dtl from PurchaseDtls table
	@GetMapping("/removePart")
	public String removePart(@RequestParam Integer dtlId, @RequestParam Integer poId) {
		service.deletePurchaseDtl(dtlId);
		Integer dtlCount = service.getPurchaseDtlCountWithPoId(poId);

		if (dtlCount == 0) {
			service.updatePurchaseOrderStatus("OPEN", poId);
		}
		return "redirect:dtls/" + poId; // POID
	}

	@GetMapping("/conformOrder/{id}")
	public String placeOrder(@PathVariable Integer id) {
		Integer dtlCount = service.getPurchaseDtlCountWithPoId(id);
		if (dtlCount > 0) {
			service.updatePurchaseOrderStatus("ORDERED", id);
		}
		return "redirect:../dtls/" + id; // POID
	}

	// 5. chnage status from ORDERED to INVOICED
	@GetMapping("/invoiceOrder/{id}")
	public String invoiceOrder(@PathVariable Integer id) {
		service.updatePurchaseOrderStatus("INVOICED", id);
		return "redirect:../all"; // POID
	}

	// 6. chnage status from ORDERED to INVOICED
	@GetMapping("/printInvoice/{id}")
	public ModelAndView printInvoice(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new VendorInvoicePdf());
		m.addObject("po", service.getOnePurchaseOrder(id).get());
		return m;
	}

}
