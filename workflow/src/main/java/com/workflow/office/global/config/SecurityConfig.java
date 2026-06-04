package com.workflow.office.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// password Encryption Been
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable()) // 잠시 off
			.authorizeHttpRequests(auth -> auth
				// 로그인, 회원가입, 비밀번호 찾기
				.requestMatchers(
						"/users/sign-up",
						"/users/sign-in", 
						"/api/users/sign-up",
						"/api/users/check-email",
						"/api/users/*/password-reset"
				).permitAll()
				// 정적 리소스
				.requestMatchers(
						"/css/**", 
						"/js/**", 
						"/images/**"
				).permitAll()
				// 회원가입에 필요한 부서/직위 API
				.requestMatchers(
						"/org/depts/dept-tree-popup", 
						"/api/org/depts/tree", 
						"/api/org/positions"
				).permitAll()
				// 관리자만 접근 페이지
				.requestMatchers("/org/**").hasRole("ADMIN")
				// 그 외 모든 요청은 로그인 해야함.
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/users/sign-in")
				.loginProcessingUrl("/login-proc")
                .usernameParameter("empNo")
                .passwordParameter("password")
				.defaultSuccessUrl("/users/profileDetails",true)
                .failureUrl("/users/sign-in?error=true")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/api/sign-out")
				.logoutSuccessUrl("/users/sign-in")
				.invalidateHttpSession(true) // 세션 무효화
				.deleteCookies("JSESSIONID") // 쿠키 삭제
			);
		
		return http.build();
	}
}
