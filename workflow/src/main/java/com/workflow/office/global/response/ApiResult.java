package com.workflow.office.global.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ApiResult<T> {
	private boolean success;
    private String message;
    private T data;
    
    public static <T> ApiResult<T> success(String message, T data){
    	return new ApiResult<>(true, message, data);
    }
    
    public static <T> ApiResult<T> success(T data){
    	return new ApiResult<>(true, null , data);
    }
}
