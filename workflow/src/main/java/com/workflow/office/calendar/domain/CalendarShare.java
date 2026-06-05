package com.workflow.office.calendar.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
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
}
