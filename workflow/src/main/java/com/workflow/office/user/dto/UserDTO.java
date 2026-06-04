package com.workflow.office.user.dto;

import java.util.List;

import com.workflow.office.user.domain.User;
import com.workflow.office.user.domain.UserAuth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDTO {
	
	@Getter
	@NoArgsConstructor
	public static class CreateRequest{
		private String empNo;
		private String name;
		private String password;
		private String email;
		private Integer deptId;
		private Integer posId;
		private Boolean noti_f;
		
		public User toEntity(String encodedPassword) {
			return User.builder()
					.empNo(this.empNo)
					.name(this.name)
					.password(encodedPassword)
					.email(this.email)
					.deptId(this.deptId)
					.posId(this.posId)
					.noti_f(this.noti_f)
					.build();			
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Integer id;
		private String empNo;
		private String name;
		private String password;
		private String email;
		private Integer deptId;
		private Integer posId;
		private Boolean noti_f;
		
		private List<UserAuth> authList;
		private String deptName;
		private String posName;
		
		public Response(User user) {
			this.id = user.getId();
			this.empNo = user.getEmpNo();
			this.name = user.getName();
			this.password = user.getPassword();
			this.email = user.getEmail();
			this.deptId = user.getDeptId();
			this.posId = user.getPosId();
			this.noti_f = user.getNoti_f();
			this.authList = user.getAuthList();
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class UserAuthResponse {
		private Integer id;
		private String authName;
	}
}
