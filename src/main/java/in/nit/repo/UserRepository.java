package in.nit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// To finding user by Email to login
	Optional<User> findByEmail(String email);

	// for activation of user
	@Modifying
	@Query("UPDATE User  SET active=:status WHERE id=:id")
	public int updateUserStatus(int status, Integer id);

	// Email count
	@Query("SELECT COUNT(U.email) FROM User U WHERE U.email=:mail")
	public Integer getUserEmailCount(String mail);

	// user count
	@Query("SELECT COUNT(U.name) FROM User U WHERE U.name=:name")
	public Integer getUserNameCount(String name);

	// Edit for Name
	@Query("SELECT COUNT(U.name) FROM User U WHERE U.name=:name AND U.id!=:id")
	public Integer getUserNameCountForEdit(String name, Integer id);

	// Edit for Email
	@Query("SELECT COUNT(U.email) FROM User U WHERE U.email=:email AND U.id!=:id")
	public Integer getUserEmailCountForEdit(String email, Integer id);
}
