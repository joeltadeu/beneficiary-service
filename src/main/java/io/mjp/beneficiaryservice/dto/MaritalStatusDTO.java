package io.mjp.beneficiaryservice.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.mjp.beneficiaryservice.domains.MaritalStatus;

public class MaritalStatusDTO implements Serializable {

	private static final long serialVersionUID = 2739962994277842252L;

	private Long id;

	@NotEmpty(message = "Required field")
	@Length(min = 5, max = 80, message = "Size must be between 5 and 80 characters")
	private String description;

	public MaritalStatusDTO() {

	}

	public MaritalStatusDTO(MaritalStatus maritalStatus) {
		this.id = maritalStatus.getId();
		this.description = maritalStatus.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
