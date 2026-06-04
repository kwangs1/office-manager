package com.workflow.office.org.deptHistory.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workflow.office.org.deptHistory.domain.DeptHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeptHistoryDTO {

	@Getter
	@NoArgsConstructor
	public static class CreateRequest {
		private Integer deptId;
		private String oldName;
		private String newName;
		private String updateReason;
		
		@lombok.Builder
		public CreateRequest(Integer deptId, String oldName, String newName, String updateReason) {
			this.deptId = deptId;
			this.oldName = oldName;
			this.newName = newName;
			this.updateReason = updateReason;
		}
		
		public DeptHistory toEntiy() {
			return DeptHistory.builder()
					.deptId(this.deptId)
					.oldName(this.oldName)
					.newName(this.newName)
					.updateReason(this.updateReason)
					.build();
		}
	}
	
	@Getter
	public static class Response {
		private final Integer id;
		private final Integer deptId;
		private final String oldName;
		private final String newName;
		private final String updateReason;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private final LocalDateTime updateDt;
		
		public Response(DeptHistory history) {
			this.id = history.getId();
			this.deptId = history.getDeptId();
			this.oldName = history.getOldName();
			this.newName = history.getNewName();
			this.updateReason = history.getUpdateReason();
			this.updateDt = history.getUpdateDt();
		}
	}
}
