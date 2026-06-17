package com.workflow.office.calendar.domain;

import java.time.LocalDateTime;

import com.workflow.office.calendar.dto.CalendarDTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarShare {
	public static final int READ   = 1; // 001
    public static final int WRITE  = 2; // 010
    public static final int MODIFY = 4; // 100
    
	private Integer calShareId;
	private Integer calMasterId;
	private String targetType;
	private Integer targetId;
	private Integer permLevel;
	private Integer regId;
	private LocalDateTime regDt;
	
	@Builder
	public CalendarShare(Integer calMasterId, String targetType, Integer targetId, Integer permLevel, Integer regId, LocalDateTime regDt) {
		this.calMasterId = calMasterId;
		this.targetType = targetType;
		this.targetId = targetId;
		this.permLevel = permLevel;
		this.regId = regId;
		this.regDt = LocalDateTime.now(); 
	}
	
	public static CalendarShare from(Integer masterId, Integer masterRegId, CalendarDTO.ShareCreateRequest request) {
		return CalendarShare.builder()
				.calMasterId(masterId)
				.targetType(request.getTargetType())
				.targetId(request.getTargetId())
				.permLevel(request.getAuthLevel())
				.regId(masterRegId)
				.build();
	}
}
