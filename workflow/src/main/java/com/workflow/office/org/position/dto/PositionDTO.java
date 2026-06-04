package com.workflow.office.org.position.dto;

import com.workflow.office.org.position.domain.Position;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PositionDTO {

	@Getter
	@NoArgsConstructor
	public static class CreateRequest {
		private String name;
		private Integer secLevel;
		
		@lombok.Builder
		public CreateRequest(String name, Integer secLevel) {
			this.name = name;
			this.secLevel = secLevel;
		}
		
		public Position toEntity() {
			return Position.builder()
					.name(this.name)
					.secLevel(this.secLevel)
					.build();
		}
	}
	
	@Getter
	public static class Response{
		private final Integer id;
        private final String name;
        private final Integer secLevel;
        
        public Response(Position position) {
        	this.id = position.getId();
        	this.name = position.getName();
        	this.secLevel = position.getSecLevel();
        }
	}
}
