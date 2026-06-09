package com.workflow.office.user.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private final User userVO;
	public CustomUserDetails(User userVO) {
        this.userVO = userVO;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (userVO.getAuthList() == null) {
			return Collections.emptyList();
		}
		return userVO.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuthName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() { return userVO.getPassword();}

	@Override
	public String getUsername() { return userVO.getEmpNo();}
	
	public User getUser() { return this.userVO;}
	
	// 계정 잠금, 만료 여부 등은 일단 true
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
