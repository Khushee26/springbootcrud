package com.n56.organization.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizationCustomResponse {

	private String organizationName;
	private String organizationCode;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String countryName;
	private Integer countryId;

}
