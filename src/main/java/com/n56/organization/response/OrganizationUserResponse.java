	package com.n56.organization.response;

import java.util.List;

import com.n56.organization.baseresponse.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationUserResponse extends BaseResponse{

	private List<OrganizationUserCustomResponse> organizationUserResponsesList;
	

}

