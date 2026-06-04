package com.workflow.office.global.response.dto;

import com.workflow.office.global.response.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String code;
	private String message;
	
	public ErrorResponse(ErrorCode errorCode, String message) {
		this.code = errorCode.getCode();
		this.message = message;
	}
}
