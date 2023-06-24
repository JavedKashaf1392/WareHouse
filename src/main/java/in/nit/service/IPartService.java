package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.Part;

public interface IPartService {

	Integer savePart(Part p);

	void updatePart(Part p);

	void deletePart(Integer id);

	Optional<Part> getOnePart(Integer id);

	List<Part> getAllParts();

	boolean isPartExist(Integer id);

	boolean isPartCodeExist(String partCode);

	boolean isPartCodeExistForEdit(String partCode, Integer id);

	Map<Integer, String> getPartIdAndCode();

	List<Object[]> getBaseCurrCount();
}
