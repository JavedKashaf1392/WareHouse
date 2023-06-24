package in.nit.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import in.nit.model.User;
import in.nit.service.IUserService;
import in.nit.util.EmailUtil;

@Controller
@RequestMapping("/user")
@SessionAttributes("userOb")
public class UserController {
	@Autowired
	private IUserService service;

	@Autowired
	private EmailUtil emailUtil;

	// 1.show UserReg Page

	@GetMapping("/register")
	public String showReg(Model model) {
		model.addAttribute("user", new User());
		return "UserRegister";
	}

	// 2. save user
	@PostMapping("/save")
	public String save(
			// read from Data from UI(given by container)
			@ModelAttribute User user, Model model // to send data to UI
	) {
		// perform save operation
		Integer id = service.saveUser(user);

		// construct one message
		if(id!=null)
		sendEmail(user);
		String message = "USER WITH '" + id + "' SAVED SUCCESSFULLY AND EMAIL ALSO SENT BY NAME:" + user.getName();
		// send message to UI
		model.addAttribute("message", message);
		// Form backing Object
		model.addAttribute("user", new User());
		// Goto Page
		return "UserRegister";
	}

	//To use multiple times 
	public void sendEmail(@ModelAttribute User user) {
		// send email on save
		
			new Thread(() -> { // LAMBDA EXPRESSION
				boolean flag = emailUtil.send(user.getEmail(), "WELCOME",
						"HELLO ! :" + user.getName() + "\nYOUR USERNAME IS:" + user.getEmail() +"\n YOUR PASSWORD :"+ user.getPassword());
				System.out.println(flag);

			}).start();
			// if(flag) message+=", Email also sent!";
			// else message+=", Email is not sent!";
		}
	
	// 3.show all
	@GetMapping("/all")
	public String fetchAll(Model model) {
		model.addAttribute("list", service.getAllUsers());
		return "UserData";
	}

	// 4.delete data
	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		String msg = null;
		if (service.isUserExist(id)) {
			service.deleteUser(id);
			msg = "USER '" + id + "' Deleted!";
		} else {
			msg = "USER '" + id + "' Not Existed!";
		}
		model.addAttribute("list", service.getAllUsers());
		model.addAttribute("message", msg);
		return "UserData";
	}

	// 5.show edit page
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id, Model model) {
		Optional<User> opt = service.getOneUser(id);
		if (opt.isPresent()) {
			User u = opt.get();
			model.addAttribute("user", u);
		} else {
			return "redirect:../all";
		}
		return "UserEdit";
	}

	// 6.update
	@PostMapping("/update")
	public String update(@ModelAttribute User user, Model model) {
		String msg = "";
		service.updateUser(user);
		msg = "USER '" + user.getId() + "' updated!";
		model.addAttribute("list", service.getAllUsers());
		model.addAttribute("message", msg);
		return "UserData";
	}

	// 7.show one
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id, Model model) {
		Optional<User> opt = service.getOneUser(id);
		if (opt.isPresent()) {
			User u = opt.get();
			model.addAttribute("ob", u);
		} else {
			return "redirect:../all";
		}
		return "UserView";
	}

	// 8. Email validation
	@GetMapping("/validateEmail")
	public @ResponseBody String validateEmail(@RequestParam String mail, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isUserEmailExist(mail)) {
			message = "USER EMAIL <b>'" + mail + "' Already exist</b>!";
		} else if (service.isUserEmailExistForEdit(mail, id)) {
			message = "USER EMAIL <b>'" + mail + "' Already exist</b>!";
		}
		return message;
	}

//9. Name validation
	@GetMapping("/validateName")
	public @ResponseBody String validateName(@RequestParam String name, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isUserNameExist(name)) {
			message = "USER NAME <b>'" + name + "' Already exist</b>!";
		} else if (service.isUserNameExistForEdit(name, id)) {
			message = "USER NAME <b>'" + name + "' Already exist</b>!";
		}
		return message;
	}

	// 10.a for status update
	// activate
	@GetMapping("/activate/{id}")
	public String activateUser(@PathVariable Integer id) {
		service.updateUserStatus(1, id);
		
	
		return "redirect:../all";
	}


	//10.b inactive
	@GetMapping("/inactive/{id}")
	public String deActivateUser(@PathVariable Integer id) {
		service.updateUserStatus(0, id);
		return "redirect:../all";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "Login";
	}
	
	@GetMapping("/setup")
	public String doSetup(Principal p,Model model,HttpSession session) 
	{
		String email = p.getName();
		System.out.println("--------->"+email);
		User user= service.findByEmail(email).get();
		model.addAttribute("userOb",user);
		//session.setMaxInactiveInterval(30);
		return "index";
	}
}
