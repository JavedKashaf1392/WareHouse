package in.nit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.Uom;
import in.nit.service.IUomService;
import in.nit.util.UomUtil;
import in.nit.view.UomExcelView;
import in.nit.view.UomPdfView;

@Controller
@RequestMapping("/uom")
public class UomcController {

	@Autowired
	private IUomService service;
	@Autowired
	private ServletContext context;
	@Autowired
	private UomUtil util;
	// 1.Get form
	// show form
	// by using get ruquest

	/*
	 * @GetMapping("/register") public String showRegister() {
	 * 
	 * return "UomRegister"; }
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("uom", new Uom());
		return "UomRegister";
	}

	// 2.save operation
	// by using Post request
	@PostMapping("/save")
	public String save(@ModelAttribute Uom uom, Model model) {

		Integer id = null;
		String message = null;
		// perform save operation
		id = service.saveUom(uom);

		// generate confirmation message
		message = "Uom '" + id + "' saved successfully";
		// send to confirmation message to UI
		model.addAttribute("message", message);
		model.addAttribute("uom", new Uom());
		// Go to page
		// return "UomRegister";
		return "UomRegister";

	}

	// 3.Displaying data
	@GetMapping("/all")
	public String fetchAll(@PageableDefault(page = 0,size = 3) Pageable pageable,Model model, 
			@RequestParam(defaultValue = "") String name
			) {
		//List<Uom> list = service.getAllUoms();
		if(name!=null) {
			model.addAttribute("page", service.findByName(name, pageable));
		}else {
			//model.addAttribute("list", service.getAllUoms());
			model.addAttribute("page", service.getAllUoms(pageable));
		}
		//Page<Uom>page =service.getAllUoms(pageable);
		//model.addAttribute("list", list);
		//model.addAttribute("page", page);
		
		return "UomData";
	}

	// 4.Deleting record

	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model,@PageableDefault(page = 0,size = 3) Pageable pageable) {
		String msg = null;
		if (service.isUomExist(id)) { // check record existence
			// invoke delete method
			try {
				service.deleteUom(id);
				msg = "Uom '" + id + "' is deleted!";
				
			} catch (DataIntegrityViolationException e) {
				msg = "Uom '" + id + "' cann't be deleted, it is current in used by Part!";
				e.printStackTrace();
			}
		} else {
			msg = "Uom '" + id + "' Not Existed!";
		}

		// display other records
		//List<Uom> list = service.getAllUoms();
		//model.addAttribute("list", list);
		model.addAttribute("page", service.getAllUoms(pageable));

		// send confirmation message to UI
		model.addAttribute("message", msg);

		// Got to page
		return "UomData";
	}

	/*
	 * @GetMapping("/shipmenttype/all") public String deletePage() { return
	 * "delete"; }
	 */

	// 5.Edit form

	@GetMapping("edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<Uom> opt = service.getOneUom(id);
		if (opt.isPresent()) {
			Uom u = opt.get();
			page = "UomEdit";
			model.addAttribute("uom", u);
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 6.update method
	@PostMapping("/update")
	public String update(@ModelAttribute Uom uom, Model model,@PageableDefault(page = 0,size = 3) Pageable pageable) {
		String msg = null;
		service.updateUom(uom);
		msg = "Uom '" + uom.getId() + "' updated successfully..";
		model.addAttribute("message", msg);
		// display other records
		List<Uom> list = service.getAllUoms();
		model.addAttribute("list", list);
		model.addAttribute("page", service.getAllUoms(pageable));
		// Go to page
		// return "UomData";
		return "UomData";

	}

	// 7.show One
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		String page = null;
		Optional<Uom> opt = service.getOneUom(id);
		if (opt.isPresent()) {
			Uom um = opt.get();
			model.addAttribute("ob", um);
			page = "UomView";
		} else {
			page = "redirect:../all";
		}
		return page;
	}

	// 8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new UomExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllUoms());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new UomExcelView());
		Optional<Uom> opt = service.getOneUom(id);
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
		m.setView(new UomPdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllUoms());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new UomPdfView());
		Optional<Uom> opt = service.getOneUom(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}	
	
	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getUomTypeCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "UomCharts";
	}
}
