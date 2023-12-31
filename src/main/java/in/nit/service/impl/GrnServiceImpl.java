package in.nit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.Grn;
import in.nit.model.GrnDtl;
import in.nit.repo.GrnDtlRepo;
import in.nit.repo.GrnRepo;
import in.nit.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {

	@Autowired
	private GrnRepo repo;
	@Autowired
	private GrnDtlRepo dtlRepo;

	@Override
	@Transactional
	public Integer saveGrn(Grn g) {
		return repo.save(g).getId();
	}

	@Override
	@Transactional
	public void updateGrn(Grn g) {
		repo.save(g);
	}

	@Override
	@Transactional
	public void deleteGrn(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Grn> getOneGrn(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grn> getAllGrns() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isGrnExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional
	public Integer saveGrnDtl(GrnDtl dtl) {
		return dtlRepo.save(dtl).getId();
	}
	@Override
	@Transactional(readOnly = true)
	public List<GrnDtl> getAllDtlsByGrnId(Integer grnId) {
		return dtlRepo.getAllDtlsByGrnId(grnId);
	}
	
	@Override
	@Transactional
	public void updateStatusByGrnDtlId(String status, Integer id) {
		dtlRepo.updateStatusByGrnDtlId(status, id);
	}
}
