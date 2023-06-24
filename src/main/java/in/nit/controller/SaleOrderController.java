package in.nit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import in.nit.model.SaleDtl;
import in.nit.model.SaleOrder;
import in.nit.service.IPartService;
import in.nit.service.ISaleOrderService;
import in.nit.service.IShipmentTypeService;
import in.nit.service.IWhUserTypeService;
import in.nit.util.SaleOrderUtil;
import in.nit.view.CustomerInvoicePdf;
import in.nit.view.SaleOrderExcelView;
import in.nit.view.SaleOrderPdfView;

@Controller
@RequestMapping("/saleorder")
public class SaleOrderController {
	private Logger logger = LoggerFactory.getLogger(OrderMethodController.class);
	@Autowired
	private ISaleOrderService service;

	@Autowired
	private IShipmentTypeService shipmentService;
	@Autowired
	private IWhUserTypeService whUserTypeService;
	@Autowired
	private IPartService partService;

	@Autowired
	private ServletContext context;

	@Autowired
	private SaleOrderUtil util;

	// create method to get integration
	private void addDorpDownUi(Model model) {
		model.addAttribute("shipmentTypes", shipmentService.getShipmentIdAndCode());
		model.addAttribute("whUserTypes", whUserTypeService.getWhUserTypeIdAndCode("Customer"));
	}

	// 1. Show Register Page

	@GetMapping("/register")
	public String showRegister(Model model) {

		model.addAttribute("saleOrder", new SaleOrder());
		addDorpDownUi(model);
		return "SaleOrderRegister";
	}

	// 2. save : on click submit

	@PostMapping("/save")
	public String save(@ModelAttribute SaleOrder saleOrder, Model model) {

		Integer id = null;
		String message = null;
		// peform save operation
		id = service.saveSaleOrder(saleOrder);
		// construct one message
		message = "Sale Order '" + id + "' saved successfully";
		// send message to UI
		model.addAttribute("message", message);
		addDorpDownUi(model);
		model.addAttribute("saleOrder", new SaleOrder());
		// got to Page
		return "SaleOrderRegister";
	}

	// 3.Displaying data:
	@GetMapping("/all")
	public String fetchAll(Model model) {
		List<SaleOrder> list = service.getAllSaleOrders();
		model.addAttribute("list", list);
		return "SaleOrderData";
	}

	// 4.delete record
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		// invoke service
		if (service.isSaleOrderExist(id)) {
			service.deleteSaleOrder(id);
			msg = "Sale Order '" + id + "' Type deleted !";
		} else {

			msg = "Sale Order'" + id + "' Not Existed !";
		}
		// display other records
		List<SaleOrder> list = service.getAllSaleOrders();
		model.addAttribute("list", list);

		// send confirmation to UI
		model.addAttribute("message", msg);
		return "SaleOrderData";
	}

	// 5.Edit form
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<SaleOrder> opt = service.getOneSaleOrder(id);
		if (opt.isPresent()) {
			SaleOrder order = opt.get();
			model.addAttribute("saleOrder", order);
			addDorpDownUi(model);
			page = "SaleOrderEdit";
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 6.update method
	@PostMapping("/update")
	public String update(@ModelAttribute SaleOrder saleOrder, Model model) {
		String msg = null;
		service.updateSaleOrder(saleOrder);
		msg = "Sale Order '" + saleOrder.getId() + "' updated successfully..";
		model.addAttribute("message", msg);

		// display other records
		List<SaleOrder> list = service.getAllSaleOrders();
		model.addAttribute("list", list);
		return "SaleOrderData";
	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page = null;
		try {
			logger.info("Calling to service");
			Optional<SaleOrder> opt = service.getOneSaleOrder(id);
			logger.info("Reading data from Object");
			SaleOrder order = opt.get();
			logger.info("Sending data to UI");
			model.addAttribute("ob", order);
			page = "SaleOrderView";
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
		m.setView(new SaleOrderExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllSaleOrders());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new SaleOrderExcelView());
		Optional<SaleOrder> opt = service.getOneSaleOrder(id);
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
		m.setView(new SaleOrderPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllSaleOrders());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new SaleOrderPdfView());
		Optional<SaleOrder> opt = service.getOneSaleOrder(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 12. ---AJAX VALIDATION----------
	// .. /shipmenttype/validatecode?code=ABCD
	@GetMapping("/validatecode")
	public @ResponseBody String validatePurchaseOrderCode(@RequestParam String code) {
		String message = "";
		if (service.isSaleOrderCodeExist(code)) {
			message = "Sale Order Code <b>'" + code + "' Already exist</b>!";
		}
		return message;
	}

	// 12. Generate Chart

	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getStockModeCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "SaleOrderCharts";
	}

	/// *************************************************************************//
	// ** SCREEN#2 OPERATIONS ***//
	// *************************************************************************//

	// add below method
	private void addDorpDownUiForDtls(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	@GetMapping("/dtls/{id}")
	public String showDtls(@PathVariable Integer id, // SO Id,
			Model model) {
		String page = null;
		// get SO using id
		SaleDtl saleDtl = new SaleDtl();
		Optional<SaleOrder> so = service.getOneSaleOrder(id);
		if (so.isPresent()) {
			model.addAttribute("so", so.get());
			// To add parts
			addDorpDownUiForDtls(model);

			// Set SO id
			saleDtl.setSo(so.get());

			model.addAttribute("saleDtl", saleDtl);
			model.addAttribute("dtlList", service.getSaleDtlWithSoId(so.get().getId()));
			page = "SaleDtls";

		} else {
			page = "redirect:../all";
		}

		return page;
	}

	// 2. on click add button
	/**
	 * Read SaleDtl object and save DB redirect to /dtls/{id} -> showDtl() method
	 */
	@PostMapping("/addPart")
	public String addPartToPo(@ModelAttribute SaleDtl saleDtl) {
		service.addPartToSo(saleDtl);
		Integer soId = saleDtl.getSo().getId();
		service.updateSaleOrderStatus("SALE-READY", soId);
		return "redirect:dtls/" + soId; // SOID
	}

	// 3 on click delete remove one dtl from SaleDtls table
	@GetMapping("/removePart")
	public String removePart(@RequestParam Integer dtlId, @RequestParam Integer soId) {
		service.deleteSaleDtl(dtlId);
		Integer dtlCount = service.getSaleDtlCountWithSoId(soId);

		if (dtlCount == 0) {
			service.updateSaleOrderStatus("SALE-OPEN", soId);
		}
		return "redirect:dtls/" + soId; // SOID
	}

	@GetMapping("/conformOrder/{id}")
	public String placeOrder(@PathVariable Integer id) {
		Integer dtlCount = service.getSaleDtlCountWithSoId(id);
		if (dtlCount > 0) {
			service.updateSaleOrderStatus("SALE-CONFIRM", id);
		}
		return "redirect:../dtls/" + id; // SOID
	}

	// 5. chnage status from ORDERED to INVOICED
	@GetMapping("/invoiceOrder/{id}")
	public String invoiceOrder(@PathVariable Integer id) {
		service.updateSaleOrderStatus("SALE-INVOICED", id);
		return "redirect:../all"; // SOID
	}

	// 6. Chnage status from ORDERED to INVOICED
	@GetMapping("/printInvoice/{id}")
	public ModelAndView printInvoice(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new CustomerInvoicePdf());
		m.addObject("so", service.getOneSaleOrder(id).get());
		return m;
	}

}
