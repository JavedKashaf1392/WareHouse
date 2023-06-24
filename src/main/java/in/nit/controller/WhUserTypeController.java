package in.nit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.WhUserType;
import in.nit.service.IWhUserTypeService;
import in.nit.util.EmailUtil;
import in.nit.util.WhUserTypeUtil;
import in.nit.view.WhUserTypeExcelView;
import in.nit.view.WhUserTypePdfView;

@Controller
@RequestMapping("/whusertype")
public class WhUserTypeController {
	@Autowired
	private IWhUserTypeService service;
	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private ServletContext context;

	@Autowired
	private WhUserTypeUtil util;

	// 1.show Reg page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("whUserType", new WhUserType());
		return "WhUserTypeRegister";
	}

	/*
	 * // 2.save data
	 * 
	 * @PostMapping("/save") public String save(@ModelAttribute WhUserType
	 * whUserType, Model model) { Integer id = null; String msg = ""; id =
	 * service.saveWhUserType(whUserType); msg = "WhUserType '" + id +
	 * "' save successfully"; model.addAttribute("message", msg);
	 * model.addAttribute("whUserType", new WhUserType()); return
	 * "WhUserTypeRegister"; }
	 */

	// 2. save : on click submit
	/**
	 * URL: /save, Type: POST Goto : WhUserTypeRegister
	 */
	/*
	 * @PostMapping("/save") public String save( //read from Data from UI(given by
	 * container)
	 * 
	 * @ModelAttribute WhUserType whUserType,
	 * 
	 * Model model //to send data to UI ) { //perform save operation Integer
	 * id=service.saveWhUserType(whUserType);
	 * 
	 * //construct one message String
	 * message="WhUserType '"+id+"' saved successfully";
	 * 
	 * //send email on save if(id!=0) { new Thread(new Runnable() { public void
	 * run() { boolean flag=emailUtil.send(whUserType.getUserEmail(), "WELCOME",
	 * "Hello User:"+whUserType.getUserCode()
	 * +", You are type:"+whUserType.getUserIdType(), fileOb );
	 * System.out.println(flag); } }).start();
	 * 
	 * boolean flag=emailUtil.send(whUserType.getUserEmail(), "WELCOME",
	 * "Hello User:"+whUserType.getUserCode()
	 * +", You are type:"+whUserType.getUserIdType()
	 * 
	 * ); if(flag) message+=", Email also sent!"; else
	 * message+=", Email is not sent!";
	 * 
	 * //send message to UI model.addAttribute("message", message); //Form backing
	 * Object model.addAttribute("whUserType", new WhUserType()); //Goto Page return
	 * "WhUserTypeRegister"; }
	 */
	@PostMapping("/save")
	public String save(
			//read from Data from UI(given by container)
			@ModelAttribute WhUserType whUserType,
			@RequestParam("fileOb") MultipartFile fileOb,
			Model model //to send data to UI
			)
	{
		//perform save operation
		Integer id=service.saveWhUserType(whUserType);

		//construct one message
		String message="WhUserType '"+id+"' saved successfully";

		//send email on save

		boolean flag=emailUtil.send(
				whUserType.getUserEmail(), 
				"WELCOME", 
				"Hello User:"+whUserType.getUserCode() 
				+", You are type:"+whUserType.getUserIdType(),
				fileOb);
		System.out.println(flag);

		if(flag) message+=", Email also sent!";
		else message+=", Email is not sent!";

		//send message to UI
		model.addAttribute("message", message);
		//Form backing Object 
		model.addAttribute("whUserType", new WhUserType());
		//Goto Page
		return "WhUserTypeRegister";
}
	// 3.show all
	@GetMapping("/all")
	public String fetchAll(Model model) {
		model.addAttribute("list", service.getAllWhUserTypes());
		return "WhUserTypeData";
	}

	// 4.delete data
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		if (service.isWhUserTypeExist(id)) {
			service.deleteWhUserType(id);
			msg = "WhUserType '" + id + "' Deleted!";
		} else {
			msg = "WhUserType '" + id + "' Not Existed!";
		}
		model.addAttribute("list", service.getAllWhUserTypes());
		model.addAttribute("message", msg);
		return "WhUserTypeData";
	}

	// 5.show edit page
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		Optional<WhUserType> opt = service.getOneWhUserType(id);
		if (opt.isPresent()) {
			WhUserType wut = opt.get();
			model.addAttribute("whUserType", wut);
		} else {
			return "redirect:../all";
		}
		return "WhUserTypeEdit";
	}

	// 6.update
	@PostMapping("/update")
	public String update(@ModelAttribute WhUserType whUserType, Model model) {
		String msg = "";
		service.updateWhUserType(whUserType);
		msg = "WhUserType '" + whUserType.getId() + "' updated!";
		model.addAttribute("list", service.getAllWhUserTypes());
		model.addAttribute("message", msg);
		return "WhUserTypeData";
	}

	// 7.show one
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		Optional<WhUserType> opt = service.getOneWhUserType(id);
		if (opt.isPresent()) {
			WhUserType wut = opt.get();
			model.addAttribute("ob", wut);
		} else {
			return "redirect:../all";
		}
		return "WhUserTypeView";
	}

	// 8. Export Data to Excel File
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		// create MAV object
		ModelAndView m = new ModelAndView();
		// set MAV view
		m.setView(new WhUserTypeExcelView());

		// send data to MAV view
		m.addObject("obs", service.getAllWhUserTypes());
		return m;
	}
	// 9. Export One row to Excel File

	@GetMapping("excel/{id}")
	public ModelAndView exportOneExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypeExcelView());
		Optional<WhUserType> opt = service.getOneWhUserType(id);
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
		m.setView(new WhUserTypePdfView());

		// send data to MAV view
		m.addObject("obs", service.getAllWhUserTypes());
		return m;
	}
	// 11. Export One row to Pdf File

	@GetMapping("pdf/{id}")
	public ModelAndView exportOnePdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypePdfView());
		Optional<WhUserType> opt = service.getOneWhUserType(id);
		if (opt.isPresent()) {
			m.addObject("obs", Arrays.asList(opt.get()));
		}
		return m;
	}

	// 12----------AJAX Validation--------------
	@GetMapping("/mailcheck")
	public @ResponseBody String validateEmail(@RequestParam String mail) {
		String message = "";
		if (service.isWhUserTypeEmailExist(mail)) {
			message = mail + " <b>already exist!</b>";
		}
		return message;
	}

	// 13. Generate Chart

	@GetMapping("/charts")
	public String generateCharts() {
		// get data from service
		List<Object[]> list = service.getWhUserTypeCount();

		// Dynamic Temp Folder location for service instance
		String location = context.getRealPath("/");

		// invoke chart methods
		util.generatePieChart(location, list);
		util.generateBarChart(location, list);
		return "WhUserTypeCharts";
	}

	// 14. ---AJAX VALIDATION----------
	@GetMapping("/validatecode")
	public @ResponseBody String validateWhUserTypeCode(@RequestParam String code) {
		String message = "";
		if (service.isWhUserTypeCodeExist(code)) {
			message = "Wh User  Code <b>'" + code + "' Already exist</b>!";
		}
		return message;
	}
}
