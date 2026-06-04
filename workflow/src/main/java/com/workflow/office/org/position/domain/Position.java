package com.workflow.office.org.position.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {

	private Integer id;
	private String name;
	private Integer secLevel;
	private LocalDateTime createAt;
	
	@Builder
	public Position(String name, Integer secLevel) {
		this.name = name;
		this.secLevel = secLevel;
		this.createAt = LocalDateTime.now();
	}
	
	public void validateCreate() {
		if (this.name == null || this.name.isBlank()) {
			throw new IllegalArgumentException("posName.required");
		}
		if (this.secLevel == null || this.secLevel < 1) {
			throw new IllegalArgumentException("pos_sec.required");
		}
	}
	
	public void changePosition(String name, Integer secLevel) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("posName.required");
		}
		if (secLevel == null || secLevel < 1) {
			throw new IllegalArgumentException("pos_sec.required");
		}
		this.name = name;
		this.secLevel = secLevel;
	}
}
