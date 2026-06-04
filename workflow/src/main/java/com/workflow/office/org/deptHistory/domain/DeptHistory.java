package com.workflow.office.org.deptHistory.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptHistory {

	private Integer id;
	private Integer deptId;
	private String oldName;
	private String newName;
	private String updateReason;
	private LocalDateTime updateDt;
	

	@Builder
	public DeptHistory(Integer deptId, String oldName, String newName, String updateReason) {
		this.deptId = deptId;
		this.oldName = oldName;
		this.newName = newName;
		this.updateReason = updateReason;
		this.updateDt = LocalDateTime.now();
	}
}
