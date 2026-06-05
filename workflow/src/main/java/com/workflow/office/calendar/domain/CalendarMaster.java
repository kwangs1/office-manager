package com.workflow.office.calendar.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarMaster {
	private Integer calMasterId;
	private String calName;
	private String calType;
	private Integer ownerId;
	private Integer deptId;
	private String useYn;
	private String delYn;
	private Integer regId;
	private LocalDateTime regDt;
	private Integer modId;
	private LocalDateTime modDt;
}
