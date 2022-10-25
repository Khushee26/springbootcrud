package com.n56.organization.exceptionhandler;

import com.n56.organization.baseresponse.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrganizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private StatusEnum resultCode;

	public OrganizationException(StatusEnum resultCode, String message) {
		super();
		this.resultCode = resultCode;
	}
}