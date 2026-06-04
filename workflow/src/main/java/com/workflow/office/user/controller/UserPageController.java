package com.workflow.office.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workflow.office.user.domain.CustomUserDetails;

@Controller
@RequestMapping("/users")
public class UserPageController {

	
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "users/sign-up";
	}
	
	@GetMapping("/sign-in")
	public String signInPage() {
		return "users/sign-in";
	}
	
	@GetMapping("/profileDetails")
	public String myProfilePage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		String empNo = userDetails.getUsername(); 
	    
	    model.addAttribute("empNo", empNo);
		return "users/profileDetails";
	}
	
}
