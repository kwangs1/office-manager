package com.workflow.office.user.service;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workflow.office.global.response.dto.DataNotFoundException;
import com.workflow.office.user.domain.User;
import com.workflow.office.user.dto.UserDTO;
import com.workflow.office.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDTO.Response signup(UserDTO.CreateRequest requestDto) {
		if (isEmailDuplicate(requestDto.getEmail())) {
			throw new IllegalArgumentException("email.duplicate");
		}
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		User user = requestDto.toEntity(encodedPassword);
		
		int result = userMapper.signup(user);	
		if (result <= 0) {
			throw new IllegalStateException("common.err.type_mismatch");
		}
		
		return new UserDTO.Response(user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isEmailDuplicate(String email) {
		return userMapper.countByEmail(email) > 0;
	}
	
	private User getValidatedUser(String empNo) {
		User user = userMapper.findByEmpNo(empNo);
		if (user == null) {
			throw new IllegalArgumentException("user.not.found");
		}
		return user;
	}

	@Override
	@Transactional
	public UserDTO.Response updateProfile(String empNo, UserDTO.CreateRequest dto) {
		User currentUser = getValidatedUser(empNo);
		
		if (dto.getEmail() != null && !dto.getEmail().equals(currentUser.getEmail())) {
			if (isEmailDuplicate(dto.getEmail())) {
				throw new IllegalArgumentException("email.duplicate");
			}
		}
		
		currentUser.updateProfile(dto.getName(), dto.getEmail(), dto.getNoti_f());
		userMapper.updateProfile(currentUser);
		
		return userMapper.findProfileByEmpNo(empNo);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO.Response getProfileInfo(String empNo) {
		UserDTO.Response profile = userMapper.findProfileByEmpNo(empNo);
		if (profile == null) {
			throw new IllegalArgumentException("profile.not.found");
		}
		return profile;
	}
	
	@Override
	@Transactional
	public String resetPassword(String empNo) {
		User user = userMapper.findByEmpNo(empNo);
		if (user == null) {
			throw new DataNotFoundException("user.not.found");
		}
		String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		
		user.changePassword(passwordEncoder.encode(tempPassword));
		userMapper.updatePassword(user);
		
		return tempPassword;
	}
}
