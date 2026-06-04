package com.workflow.office.user.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuth {

	private Integer id;
	private Integer userId;
	private String authName;
	private LocalDateTime grantedAt;
	private Integer grantedBy;
	
	@Builder
	private UserAuth(Integer userId, String authName, Integer grantedBy) {
		this.userId = userId;
		this.authName = authName;
		this.grantedBy = grantedBy;
		this.grantedAt = LocalDateTime.now();
	}
}
