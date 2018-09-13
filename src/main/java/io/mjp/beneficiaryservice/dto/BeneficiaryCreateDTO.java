package io.mjp.beneficiaryservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.mjp.beneficiaryservice.domains.Beneficiary;
import io.mjp.beneficiaryservice.domains.enums.Gender;
import io.mjp.beneficiaryservice.services.validation.BeneficiaryValidation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@BeneficiaryValidation
@ApiModel(value = "Beneficiary", description = "Beneficiary resource representation")
public class BeneficiaryCreateDTO implements Serializable {

	private static final long serialVersionUID = 5803157273525904817L;

	@ApiModelProperty(value = "Beneficiary's ID", required = false)
	private Long id;

	@ApiModelProperty(value = "Beneficiary's CPF", required = true)
	@CPF(message = "CPF invalid")
	@NotEmpty(message = "Required field")
	private String cpf;

	@ApiModelProperty(value = "Beneficiary's rg", required = false)
	private String rg;

	@ApiModelProperty(value = "Beneficiary's name", required = true)
	@NotEmpty(message = "Required field")
	private String name;

	@ApiModelProperty(value = "Beneficiary's birth date", required = true)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@ApiModelProperty(value = "Beneficiary's gender", required = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@ApiModelProperty(value = "Beneficiary's schooling", required = false)
	private Long schoolingId;

	@ApiModelProperty(value = "Beneficiary's marital status", required = false)
	private Long maritalStatusId;

	public BeneficiaryCreateDTO() {

	}

	public BeneficiaryCreateDTO(Beneficiary beneficiary) {
		this.id = beneficiary.getId();
		this.name = beneficiary.getName();
		this.rg = beneficiary.getRg();
		this.cpf = beneficiary.getCpf();
		this.birthDate = beneficiary.getBirthDate();
		this.gender = beneficiary.getGender();
		this.maritalStatusId = beneficiary.getMaritalStatus().getId();
		this.schoolingId = beneficiary.getSchooling().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthLocalDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getSchoolingId() {
		return schoolingId;
	}

	public void setSchoolingId(Long schoolingId) {
		this.schoolingId = schoolingId;
	}

	public Long getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(Long maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeneficiaryCreateDTO other = (BeneficiaryCreateDTO) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
