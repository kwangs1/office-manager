package com.workflow.office.calendar.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarShare {
	private Integer calShareId;
	private Integer calMasterId;
	private String targetType;
	private Integer targetId;
	private String permLevel;
	private Integer regId;
	private LocalDateTime regDt;
	
	@Builder
	public CalendarShare(Integer calMasterId, String targetType, Integer targetId, String permLevel, Integer regId, LocalDateTime regDt) {
		this.calMasterId = calMasterId;
		this.targetType = targetType;
		this.targetId = targetId;
		this.permLevel = permLevel;
		this.regId = regId;
		this.regDt = LocalDateTime.now(); 
	}
}
