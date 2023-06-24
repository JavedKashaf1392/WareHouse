package in.nit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.OrderMethod;
import in.nit.service.IOrderMethodService;
import in.nit.spec.OrderMethodSpec;
import in.nit.util.OrderMethodUtil;
import in.nit.view.OrderMethodExcelView;
import in.nit.view.OrderMethodPdfView;

@Controller
@RequestMapping("/ordermethod")
public class OrderMethodController {

	private Logger log = LoggerFactory.getLogger(OrderMethodController.class);
	@Autowired
	private IOrderMethodService service;

	@Autowired
	private ServletContext context;
	@Autowired
	private OrderMethodUtil util;

	// 1.show Reg Page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}

	// 2.save data
	@PostMapping("/save")
	public String save(@ModelAttribute OrderMethod orderMethod, Model model) {
		Integer id = null;
		String msg = null;
		id = service.saveOrderMethod(orderMethod);
		msg = "Order Method '" + id + "'save successfully";
		model.addAttribute("message", msg);
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}

	// 3.show all
	@GetMapping("/all")
	public String fetchAll(@ModelAttribute OrderMethod orderMehtod, Model model) {
		Specification<OrderMethod> spec = new OrderMethodSpec(orderMehtod);
		model.addAttribute("list", service.getAllOrderMethods(spec));
		model.addAttribute("orderMehtod", orderMehtod);// form backing object
		return "OrderMethodData";
	}

	// 4.delete record
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		if (service.isOrderMethodExist(id)) {
			service.deleteOrderMethod(id);
			msg = "Order Method '" + id + "'Deleted!";
		} else {
			msg = "Order Method '" + id + "'Not Existed!";
		}
		model.addAttribute("list", service.getAllOrderMethods());
		model.addAttribute("message", msg);
		return "OrderMethodData";
	}

	// 5.show edit form

	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<OrderMethod> opt = service.getOneOrderMethod(id);
		if (opt.isPresent()) {
			OrderMethod om = opt.get();
			model.addAttribute("orderMethod", om);
			page = "OrderMethodEdit";
		} else {
			page = "OrderMethodData";
		}
		return page;
	}

	// 6.update
	@PostMapping("/update")
	public String update(@ModelAttribute OrderMethod orderMethod, Model model) {
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "Order Method '" + orderMethod.getId() + "' Updated!");
		model.addAttribute("list", service.getAllOrderMethods());
		return "OrderMethodData";
	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		try {
			log.info("Making Service call");
			Optional<OrderMethod> opt = service.getOneOrderMethod(id);
			log.info("Reading Data from Optional Object");
			OrderMethod om = opt.get();
			model.addAttribute("ob", om);
		} catch (NoSuchElementException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "OrderMethodView";
	}

	// 8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new OrderMethodExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllOrderMethods());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodExcelView());
		Optional<OrderMethod> opt = service.getOneOrderMethod(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 8. Export Data to Excel File
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new OrderMethodPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllOrderMethods());
		return m;
	}
	// 12. Generate Chart

	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getOrderModeCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "OrderMethodCharts";
	}
}
