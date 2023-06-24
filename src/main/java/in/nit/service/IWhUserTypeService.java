package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.WhUserType;

public interface IWhUserTypeService {

	Integer saveWhUserType(WhUserType wut);

	void updateWhUserType(WhUserType wut);

	void deleteWhUserType(Integer id);

	Optional<WhUserType> getOneWhUserType(Integer id);

	List<WhUserType> getAllWhUserTypes();

	boolean isWhUserTypeExist(Integer id);

	boolean isWhUserTypeCodeExist(String code);

	boolean isWhUserTypeEmailExist(String mail);

	List<Object[]> getWhUserTypeCount();

	Map<Integer, String> getWhUserTypeIdAndCode(String userType);
}
