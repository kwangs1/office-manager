package com.workflow.office.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.workflow.office.user.domain.User;
import com.workflow.office.user.dto.UserDTO;

@Mapper
public interface UserMapper {

	int signup(User vo);
	
	int countByEmail(String email);
	
	User findWithRolesByEmpNo(String empNo);
	
	User findByEmpNo(String empNo);
	
	UserDTO.Response findProfileByEmpNo(String empNo);

	void updateProfile(User currentUser);
	
	void updatePassword(User user);
}
