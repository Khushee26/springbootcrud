package com.n56.organization.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OrganizationRequest {

	
	private String organizationId;
	@NotNull
	@NotEmpty
	private String organizationName;
	@NotNull
	@NotEmpty
	private String organizationCode;
	private String organizationAddressLine1;
	private String organizationAddressLine2;
	private String organizationAddressLine3;
	private String organizationCity;
	private Integer organizationCountryId;
	
}
