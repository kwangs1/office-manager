package com.workflow.office.org.dept.dto;

import java.time.LocalDateTime;

import com.workflow.office.org.dept.domain.Department;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class DepartmentDTO {

	@Getter
	@NoArgsConstructor
	public static class CreateRequest{
		private String name;
		private Integer pid;
		private Integer depth;
		private Integer sort_or;
	}
	
	@Getter
	@NoArgsConstructor
	public static class UpdateRequest{
		private String name;
		private Integer sortOr;
		private String status;
		private Integer pid;
		private String updateReason;
	}
	
	@Getter
	public static class Response{
		private final Integer id;
        private final String name;
        private final Integer pid;
        private final Integer depth;
        private final Integer sortOr;
        private final String status;
        private final LocalDateTime createdAt;
        private final String updateReason;
        
		public Response(Department dept) {
			this.id = dept.getId();
			this.name = dept.getName();
			this.pid = dept.getPid();
			this.depth = dept.getDepth();
			this.sortOr = dept.getSortOr();
			this.status = dept.getStatus();
			this.createdAt = dept.getCreatedAt();
			this.updateReason = dept.getUpdateReason();
		}
	}
	
	
}
