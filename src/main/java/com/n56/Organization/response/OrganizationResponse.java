package com.n56.Organization.response;

import java.util.List;

import com.n56.Organization.BaseResponse.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationResponse extends BaseResponse{

	private List<OrganizationCustomResponse> organizationResponsesList;
}
