package com.n56.organization.response;

import java.util.List;

import com.n56.organization.baseresponse.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationResponse extends BaseResponse{

	private List<OrganizationCustomResponse> organizationResponsesList;
}

