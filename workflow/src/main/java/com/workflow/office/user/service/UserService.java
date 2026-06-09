package com.workflow.office.user.service;

import java.util.List;

import com.workflow.office.user.domain.User;
import com.workflow.office.user.dto.UserDTO;

public interface UserService {
	
	List<User> getUserList(Integer deptId);

	UserDTO.Response signup(UserDTO.CreateRequest requestDto);
	
	boolean isEmailDuplicate(String email);
	
	UserDTO.Response getProfileInfo(String empNo);
	
	UserDTO.Response updateProfile(String empNo, UserDTO.CreateRequest dto);
	
	String resetPassword(String empNo);
}
