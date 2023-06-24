package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.User;
import in.nit.repo.UserRepository;
import in.nit.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {
	@Autowired
	private UserRepository repo; //HAS-A
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Integer saveUser(User u) {
		String pwd = u.getPassword();
		String encPwd = encoder.encode(pwd);
		u.setPassword(encPwd);
		u = repo.save(u);
		return u.getId();
	}
	

	@Override
	@Transactional
	public void updateUser(User u) {
		String pwd = u.getPassword();
		String encPwd = encoder.encode(pwd);
		u.setPassword(encPwd);
		u = repo.save(u);
		repo.save(u);
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getOneUser(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserNameExist(String name) {
		return repo.getUserNameCount(name) > 0 ? true : false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserEmailExist(String mail) {
		return repo.getUserEmailCount(mail) > 0 ? true : false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserNameExistForEdit(String name, Integer id) {
		return repo.getUserNameCountForEdit(name, id)>0?true:false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUserEmailExistForEdit(String email, Integer id) {
		return repo.getUserEmailCountForEdit(email, id)>0?true:false;
	}

	@Override
	@Transactional
	public int updateUserStatus(int status, Integer id) {
		return repo.updateUserStatus(status, id);
	}
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) 
		
		throws UsernameNotFoundException {
			Optional<User> opt = repo.findByEmail(username);
			if(!opt.isPresent()|| opt.get().getActive()==0)
				throw new UsernameNotFoundException("User Not found");
				
			
			User user = opt.get();
			
			return new org.springframework.security.core.userdetails.User(
					username, 
					user.getPassword(), 
					user.getRoles()
					.stream()
					.map(role->new SimpleGrantedAuthority(role))
					.collect(Collectors.toSet())
					);
	}
	@Override
	public Optional<User> findByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	@Override
	public List<Object[]> getUserCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> getUserIdAndCode(String userType) {
		// TODO Auto-generated method stub
		return null;
	}

}
