package com.n56.organization.request;
import javax.validation.constraints.Email;

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

public class OrganizationUserRequest {
	
	 private Integer organizationId;
	 private String firstName;
	 private String lastName;
	 private String addressLine1;
	 private String addressLine2;
	 private String addressLine3;
	 private String organizationUserCity;
	 private Integer countryId;
	 @Email(message = "please verify your email")
	 private String organizationUserEmail;
	 private String organizationUserPassword;

}
