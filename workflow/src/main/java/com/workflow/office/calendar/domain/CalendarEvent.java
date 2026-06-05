package com.workflow.office.calendar.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarEvent {
	private Integer eventId;
	private Integer calMasterId;
	private String title;
	private String content;
	private LocalDateTime startDt;
	private LocalDateTime endDt;
	private String allDayYn;
	private String colorCode;
	private String isRepeat;
	private String repeatType;
	private LocalDateTime repeatEndDt;
	private Integer regId;
	private LocalDateTime regDt;
	private Integer modId;
	private LocalDateTime modDt;
}
