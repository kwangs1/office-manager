package com.workflow.office.global.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
	
	METHOD_NOT_ALLOWED("COMMON_405", "common.err.method_not_allowed"),
	TYPE_MISMATCH("COMMON_400", "common.err.type_mismatch"),
	DATA_NOT_FOUND("DATA_404", "common.err.not_found"),
	BUSINESS_ERROR("BIZ_ERR", null),
	SERVER_ERROR("SERVER_500", "common.err.server_error");
	
	private final String code;
	private final String messageKey;
	
	ErrorCode(String code, String messageKey){
		this.code = code;
		this.messageKey = messageKey;
	}
}
