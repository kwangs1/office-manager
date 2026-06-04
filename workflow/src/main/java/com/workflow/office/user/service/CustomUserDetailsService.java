package com.workflow.office.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.workflow.office.user.domain.CustomUserDetails;
import com.workflow.office.user.domain.User;
import com.workflow.office.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {
		User user = userMapper.findWithRolesByEmpNo(empNo);
		
		if (user == null) {
			throw new UsernameNotFoundException("존재하지 않는 유저입니다: "+empNo);
		}
		return new CustomUserDetails(user);
	}

}
