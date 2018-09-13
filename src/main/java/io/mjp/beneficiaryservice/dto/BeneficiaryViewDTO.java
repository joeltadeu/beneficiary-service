package io.mjp.beneficiaryservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import io.mjp.beneficiaryservice.domains.Beneficiary;
import io.mjp.beneficiaryservice.domains.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Beneficiary", description = "Beneficiary resource representation")
public class BeneficiaryViewDTO implements Serializable {

	private static final long serialVersionUID = 5803157273525904817L;

	@ApiModelProperty(value = "Beneficiary's ID", required = false)
	private Long id;

	@ApiModelProperty(value = "Beneficiary's CPF", required = true)
	private String cpf;

	@ApiModelProperty(value = "Beneficiary's rg", required = false)
	private String rg;

	@ApiModelProperty(value = "Beneficiary's name", required = true)
	private String name;

	@ApiModelProperty(value = "Beneficiary's birth date", required = true)
	private LocalDate birthDate;

	@ApiModelProperty(value = "Beneficiary's gender", required = true)
	private Gender gender;

	@ApiModelProperty(value = "Beneficiary's schooling", required = false)
	private SchoolingDTO schooling;

	@ApiModelProperty(value = "Beneficiary's marital status", required = false)
	private MaritalStatusDTO maritalStatus;

	public BeneficiaryViewDTO(Beneficiary beneficiary) {
		this.id = beneficiary.getId();
		this.name = beneficiary.getName();
		this.rg = beneficiary.getRg();
		this.cpf = beneficiary.getCpf();
		this.birthDate = beneficiary.getBirthDate();
		this.gender = beneficiary.getGender();
		this.maritalStatus = new MaritalStatusDTO(beneficiary.getMaritalStatus());
		this.schooling = new SchoolingDTO(beneficiary.getSchooling());
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

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public SchoolingDTO getSchooling() {
		return schooling;
	}

	public void setSchooling(SchoolingDTO schooling) {
		this.schooling = schooling;
	}

	public MaritalStatusDTO getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatusDTO maritalStatus) {
		this.maritalStatus = maritalStatus;
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
		BeneficiaryViewDTO other = (BeneficiaryViewDTO) obj;
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
