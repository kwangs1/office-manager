package com.workflow.office.user.service;

import com.workflow.office.user.dto.UserDTO;

public interface UserService {

	UserDTO.Response signup(UserDTO.CreateRequest requestDto);
	
	boolean isEmailDuplicate(String email);
	
	UserDTO.Response getProfileInfo(String empNo);
	
	UserDTO.Response updateProfile(String empNo, UserDTO.CreateRequest dto);
	
	String resetPassword(String empNo);
}
