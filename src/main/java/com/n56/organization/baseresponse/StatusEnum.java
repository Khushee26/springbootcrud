package com.n56.organization.baseresponse;

public enum StatusEnum {

	SUCCESS("Success"), NOT_FOUND("Data not found."), ID_NOT_EXISTS("This id doesn't exists");

	String statusCode;

	private StatusEnum(String statusCode) {
		this.statusCode = statusCode;
	}

}
