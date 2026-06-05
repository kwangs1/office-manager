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
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/users/sign-up",
						"/users/sign-in", 
						"/api/users/sign-up",
						"/api/users/check-email",
						"/api/users/*/password-reset",
						"/org/depts/dept-tree-popup",
						"/api/org/depts/tree",
						"/api/org/positions",
						"/css/**", 
						"/js/**", 
						"/images/**"
				).permitAll()
				.requestMatchers("/org/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/users/sign-in")
				.loginProcessingUrl("/login-proc")
                .usernameParameter("empNo")
                .passwordParameter("password")
				.defaultSuccessUrl("/calendars",true)
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
