package com.n56.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.baseresponse.StatusEnum;
import com.n56.organization.repository.OrganizationRepository;
import com.n56.organization.request.OrganizationRequest;
import com.n56.organization.response.OrganizationResponse;
import com.n56.organization.service.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	OrganizationRepository organizationRepository;
	
	@PostMapping("save")
	private BaseResponse saveOrganizationData(@RequestBody OrganizationRequest organizationRequest) {
		organizationService.saveOrganizationData(organizationRequest, organizationRequest.getOrganizationCountryId());
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(StatusEnum.SUCCESS);
		baseResponse.setMessage( "Organization's data saved successfully ");
		return baseResponse;
	}
	
	@PostMapping("/update/{organizationId}")	
	private BaseResponse updateOrganizationData(@RequestBody OrganizationRequest organizationRequest,@PathVariable("organizationId") int organizationId) {	
		return organizationService.updateOrganizaitonData(organizationRequest, organizationId);
	}

	@DeleteMapping("/delete/{organizationId}")
	private BaseResponse deleteOrganizationData(@PathVariable("organizationId") int organizationId) {
		return organizationService.deleteOrganizationData(organizationId);
	}

	@GetMapping("/get/{organizationId}")
	@ResponseBody
	private ResponseEntity<?> getOrganizationDataById(@PathVariable("organizationId") int organizationId) {
		OrganizationResponse organizationResponse = null;
		try {
			organizationResponse = new OrganizationResponse();
			organizationResponse = organizationService.getOrganizationDataById(organizationId);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(organizationResponse);
	}
	
	@GetMapping ("/getList")
	private ResponseEntity<?> getOrganizationInList() {
		OrganizationResponse organizationResponse = organizationService.getOrganizationInList();
		return ResponseEntity.status(HttpStatus.OK).body(organizationResponse);
	}
	
}