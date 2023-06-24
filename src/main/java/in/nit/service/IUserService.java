package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.User;

public interface IUserService {

	Integer saveUser(User u);

	void updateUser(User u);

	void deleteUser(Integer id);

	Optional<User> getOneUser(Integer id);

	List<User> getAllUsers();

	boolean isUserExist(Integer id);

	boolean isUserNameExist(String name);

	boolean isUserNameExistForEdit(String name, Integer id);

	boolean isUserEmailExistForEdit(String email, Integer id);

	boolean isUserEmailExist(String mail);

	List<Object[]> getUserCount();

	Map<Integer, String> getUserIdAndCode(String userType);

	// Security setup
	Optional<User> findByEmail(String email);
	//To activate user
	int updateUserStatus(int status, Integer id);
}
