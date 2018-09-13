package io.mjp.beneficiaryservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.mjp.beneficiaryservice.domains.Beneficiary;
import io.mjp.beneficiaryservice.domains.MaritalStatus;
import io.mjp.beneficiaryservice.domains.Schooling;
import io.mjp.beneficiaryservice.dto.BeneficiaryCreateDTO;
import io.mjp.beneficiaryservice.repositories.BeneficiaryRepository;
import io.mjp.beneficiaryservice.services.exceptions.ObjectNotFoundException;

@Service
public class BeneficiaryService {

	@Autowired
	private BeneficiaryRepository repository;

	public Beneficiary find(Long id) {
		Optional<Beneficiary> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Beneficiary not found! Id: " + id));
	}

	public Beneficiary insert(Beneficiary beneficiary) {
		return repository.save(beneficiary);
	}

	public Beneficiary update(Beneficiary beneficiary) {
		Beneficiary newBeneficiary = find(beneficiary.getId());
		updateData(newBeneficiary, beneficiary);
		return repository.save(newBeneficiary);
	}

	private void updateData(Beneficiary newObj, Beneficiary obj) {
		newObj.setName(obj.getName());
		newObj.setGender(obj.getGender());
		newObj.setBirthDate(obj.getBirthDate());
		newObj.setRg(obj.getRg());
		newObj.setMaritalStatus(obj.getMaritalStatus());
		newObj.setSchooling(obj.getSchooling());
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}

	public List<Beneficiary> findAll() {
		return repository.findAll();
	}

	public Beneficiary findByCpf(String cpf) {
		Optional<Beneficiary> obj = repository.findByCpf(cpf);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Beneficiary not found! cpf: " + cpf));
	}

	public Page<Beneficiary> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Beneficiary fromDTO(BeneficiaryCreateDTO dto) {
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setName(dto.getName());
		beneficiary.setGender(dto.getGender());
		beneficiary.setBirthDate(dto.getBirthDate());
		beneficiary.setRg(dto.getRg());
		beneficiary.setMaritalStatus(new MaritalStatus(dto.getMaritalStatusId()));
		beneficiary.setSchooling(new Schooling(dto.getSchoolingId()));
		return beneficiary;
	}
}
