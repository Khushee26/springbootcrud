package com.n56.Organization.exceptionhandler;

import com.n56.Organization.BaseResponse.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor 
public class OrganizationNotFoundException extends RuntimeException{
 
	private static final long serialVersionUID = 1L;
	private StatusEnum resultCode;

	public OrganizationNotFoundException(StatusEnum resultCode, String message) {
		super(message);
		this.resultCode = resultCode;
	}
}

