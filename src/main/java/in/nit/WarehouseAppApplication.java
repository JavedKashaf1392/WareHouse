package in.nit;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import in.nit.model.User;
import in.nit.repo.UserRepository;
import in.nit.service.IUserService;

@SpringBootApplication
public class WarehouseAppApplication implements CommandLineRunner {

	@Autowired
	private IUserService userService;
	public static void main(String[] args) {
		SpringApplication.run(WarehouseAppApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		
		
		  Set<String>role= new HashSet<>(); 
//		  role.add("ADMIN");
//		   role.add("EMPLOYEE");
//		  userService.saveUser(new User(1, "admin", "admin@gmail.com", "admin", 1,
//		  role.stream().collect(Collectors.toSet())));
		 
//		userService.saveUser(new User(2, "Mohammad Javed",
//		   "javed@gmail.com", "javed", 1,
//		   role));
	}
	
	

}
