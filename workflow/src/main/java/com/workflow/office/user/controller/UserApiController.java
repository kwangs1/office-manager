package com.workflow.office.user.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.office.global.response.ApiResult;
import com.workflow.office.user.domain.User;
import com.workflow.office.user.dto.UserDTO;
import com.workflow.office.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

	private final UserService userService;
	private final MessageSource messageSource;
	

	@GetMapping("/{deptId}")
	public ResponseEntity<ApiResult<List<UserDTO.Response>>> getUserList(@PathVariable("deptId") Integer deptId) {
		List<User> userList = userService.getUserList(deptId);

		List<UserDTO.Response> dtoList = userList.stream()
		                                         .map(user -> new UserDTO.Response(user)) 
		                                         .collect(Collectors.toList());
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, dtoList));	
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResult<UserDTO.Response>> signup(@RequestBody UserDTO.CreateRequest requestDto) {
		UserDTO.Response responseDto = userService.signup(requestDto);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@GetMapping("/check-email")
	public ResponseEntity<ApiResult<Boolean>> checkEmailDuplicate(@RequestParam("email") String email){
		boolean isDuplicate = userService.isEmailDuplicate(email);
		return ResponseEntity.ok(ApiResult.success(isDuplicate));
	}
	
	@GetMapping("/{empNo}/getProfileInfo")
	public ResponseEntity<ApiResult<UserDTO.Response>> getProfileInfo(@PathVariable("empNo") String empNo){
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, userService.getProfileInfo(empNo)));
	}
	
	@PatchMapping("/{empNo}/updateProfile")
	public ResponseEntity<ApiResult<UserDTO.Response>> updateProfile(@PathVariable("empNo") String empNo, @RequestBody UserDTO.CreateRequest updateDto){
		UserDTO.Response responseDto = userService.updateProfile(empNo, updateDto);
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@PostMapping("/{empNo}/password-reset")
	public ResponseEntity<ApiResult<String>> resetPassword(@PathVariable("empNo") String empNo){
		String newPassword = userService.resetPassword(empNo);
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, newPassword));
	}
	
}