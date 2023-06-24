package in.nit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

	/**
	 * Index page
	 * */
	@GetMapping("/")
	public String indexPage() {
		return "index";
	}
	@GetMapping("/index")
	public String indexPag() {
		return "index";
	}
}
