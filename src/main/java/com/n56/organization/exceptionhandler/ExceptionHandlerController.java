package com.n56.organization.exceptionhandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.n56.organization.baseresponse.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

	//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(value = OrganizationNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public BaseResponse handleOrganizationNotFoundException(OrganizationNotFoundException e) { 
		//log.info(null);
		LOGGER.error("handleOrganizationNotFoundException: errors={}", e.getMessage());
		return new BaseResponse(e.getResultCode(), e.getMessage());
	}

		@ExceptionHandler (value = OrganizationException.class)
		@ResponseStatus(NOT_FOUND)
		public BaseResponse handleOrganizationException(OrganizationException e) {
			LOGGER.error("handleOrganizationException: errors={}", e.getMessage());
			return new BaseResponse(e.getResultCode(),e.getMessage());

		}
		
		
		@ExceptionHandler (value = OrganizationDuplicationDataException.class) 
		@ResponseStatus (NOT_FOUND)
		public BaseResponse handleOrganizationDuplicationDataException (OrganizationDuplicationDataException e) {
			LOGGER.error("handleOrganizationDuplicationDataException: errors={}", e.getMessage());
			return new BaseResponse(e.getResultCode(), e.getMessage());
		}
}
