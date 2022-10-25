package com.n56.organization.response;

import com.n56.organization.baseresponse.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrganizationUserCustomResponse {

	private String organizationName;
	private String organizationCode;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String countryName;
	private Integer countryId;

}
