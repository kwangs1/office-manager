package com.workflow.office.org.dept.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {
	private Integer id;
	private String name;
	private Integer pid;
	private Integer depth;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private Integer sortOr;
	private String status;
	private String updateReason;
	

	@Builder
	public Department(String name, Integer pid, Integer depth, Integer sortOr, String status) {
		this.name = name;
		this.pid = pid;
		this.depth = depth;
		this.sortOr = sortOr != null ? sortOr : 0;
		this.status = status != null ? status : "1";
		this.createdAt = LocalDateTime.now();
	}

	public void validateCreate() {
		if (this.name == null || this.name.isBlank()) {
			throw new IllegalArgumentException("deptname.required");
		}
	}
	public void updateDepartment(String name, Integer sortOr, Integer pid,  String status) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("new_deptname.required");
		}
		this.name = name;
		this.sortOr = sortOr;
		this.pid = pid;
		this.status = status;
	}
}
