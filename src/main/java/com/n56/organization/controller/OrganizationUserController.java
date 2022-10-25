
package com.n56.organization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n56.organization.baseresponse.BaseResponse;
import com.n56.organization.baseresponse.StatusEnum;
import com.n56.organization.exceptionhandler.OrganizationNotFoundException;
import com.n56.organization.request.OrganizationUserRequest;
import com.n56.organization.response.OrganizationResponse;
import com.n56.organization.response.OrganizationUserResponse;
import com.n56.organization.service.OrganizationUserService;

@RestController
@RequestMapping("/organizationuser")
public class OrganizationUserController {

	@Autowired
	private OrganizationUserService organizationUserService;

	@PostMapping("/save")
	public BaseResponse saveOrganizationUserData(@RequestBody @Valid OrganizationUserRequest organizationUserRequest) {
		return organizationUserService.saveOrganizationUserData(organizationUserRequest, null);
//		BaseResponse baseResponse = new BaseResponse();
//		baseResponse.setStatus(StatusEnum.SUCCESS);
//		baseResponse.setMessage( "Organizationuser data saved successfully ");
//		return baseResponse;
	}
	
	@PostMapping("/update/{organizationuserId}")
	public ResponseEntity<?> updateOrganizationUserData (@RequestBody OrganizationUserRequest organizationUserRequest, 
			@PathVariable("organizationuserId") int organizationUserId ){
		return ResponseEntity.ok(organizationUserService.saveOrganizationUserData(organizationUserRequest, organizationUserId));
	}
	
	@DeleteMapping("/delete/{organizationUserId}")
	public ResponseEntity<?> deleteOrganizationUserData(@PathVariable("organizationUserId") int organizationUserId) {
		BaseResponse baseResponse = organizationUserService.deleteOrganizationUserData(organizationUserId);
		return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
	}
	
	@GetMapping("/get/{organizationUserId}")
	public ResponseEntity<?> getOrganizationUserDataById(@PathVariable("organizationUserId") int oranizationUserId ){
		OrganizationUserResponse organizationUserResponse = null;
		try {
			organizationUserResponse = new OrganizationUserResponse();
			organizationUserResponse = organizationUserService.getOrganizationUserDataById(oranizationUserId);				
		} catch (OrganizationNotFoundException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(organizationUserService.getOrganizationUserDataById(oranizationUserId));
	}
	
	@GetMapping("/getList")
	public ResponseEntity<?> getOrganizationUserList(){
		return ResponseEntity.ok(organizationUserService.getOrganizationUserList());
	}
}