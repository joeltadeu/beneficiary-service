package io.mjp.beneficiaryservice.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import io.mjp.beneficiaryservice.domains.Beneficiary;
import io.mjp.beneficiaryservice.domains.MaritalStatus;
import io.mjp.beneficiaryservice.domains.Schooling;
import io.mjp.beneficiaryservice.dto.BeneficiaryCreateDTO;
import io.mjp.beneficiaryservice.repositories.BeneficiaryRepository;
import io.mjp.beneficiaryservice.repositories.MaritalStatusRepository;
import io.mjp.beneficiaryservice.repositories.SchoolingRepository;
import io.mjp.beneficiaryservice.resources.exceptions.FieldMessage;

public class BeneficiaryValidator implements ConstraintValidator<BeneficiaryValidation, BeneficiaryCreateDTO> {

	@Autowired
	HttpServletRequest request;

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	@Autowired
	SchoolingRepository schoolingRepository;

	@Autowired
	MaritalStatusRepository maritalStatusRepository;

	@Override
	public boolean isValid(BeneficiaryCreateDTO dto, ConstraintValidatorContext context) {
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		List<FieldMessage> errors = new ArrayList<>();

		Optional<Beneficiary> optionalBeneficiary = beneficiaryRepository.findByCpf(dto.getCpf());

		if (!map.isEmpty()) {
			String uriCpf = map.get("cpf");

			if (optionalBeneficiary.isPresent() && !optionalBeneficiary.get().getId().equals(uriCpf)) {
				errors.add(new FieldMessage("cpf", "Existing CPF"));
			}
		} else {
			if (optionalBeneficiary.isPresent()) {
				errors.add(new FieldMessage("cpf", "Existing CPF"));
			}
		}

		if (dto.getSchoolingId() != null) {
			Optional<Schooling> optionalSchooling = schoolingRepository.findById(dto.getSchoolingId());
			if (!optionalSchooling.isPresent())
				errors.add(new FieldMessage("schoolingId", "Schooling is not found"));
		}

		if (dto.getMaritalStatusId() != null) {
			Optional<MaritalStatus> optionalMaritalStatus = maritalStatusRepository.findById(dto.getMaritalStatusId());
			if (!optionalMaritalStatus.isPresent())
				errors.add(new FieldMessage("maritalStatusId", "Marital status is not found"));
		}

		for (FieldMessage field : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(field.getMessage()).addPropertyNode(field.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}
}
