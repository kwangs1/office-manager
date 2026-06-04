package com.workflow.office.user.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	private Integer id;
	private String empNo;
	private String name;
	private String password;
	private String email;
	private Integer deptId;
	private Integer posId;
	private Boolean noti_f;
	private LocalDateTime pwUpdateAt;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private String status;
	
	private List<UserAuth> authList;
	
	@Builder
	private User(String empNo, String name, String password, String email, Integer deptId, Integer posId, Boolean noti_f, String status, LocalDateTime createdAt){
		this.empNo = empNo;
		this.name = name;
		this.password = password;
		this.email = email;
		this.deptId = deptId;
		this.posId = posId;
		this.noti_f = noti_f != null ? noti_f : true;
		this.status = "1";
		this.createdAt = LocalDateTime.now();
	}
	public void changePassword(String encryptedPassword) {
		this.password = encryptedPassword;
		this.pwUpdateAt = LocalDateTime.now();
		this.updateAt = LocalDateTime.now();
	}
	
	public void assignOranization(Integer deptId, Integer posId) {
		this.deptId = deptId;
		this.posId = posId;
		this.updateAt = LocalDateTime.now();
	}
	
	public void updateProfile(String newName, String newEmail, Boolean newNotiF) {
		boolean isChanged = false;
		
		if (newName != null && !newName.isBlank()) {
			this.name = newName;
			isChanged = true;
		}
		
		if (newEmail != null && !newEmail.isBlank()) {
			this.email = newEmail;
			isChanged = true;
		}
		
		if (newNotiF != null) {
			this.noti_f = Boolean.TRUE.equals(newNotiF);
			isChanged = true;
		}
		
		if (isChanged) {
			this.updateAt = LocalDateTime.now();
		}
	}
}