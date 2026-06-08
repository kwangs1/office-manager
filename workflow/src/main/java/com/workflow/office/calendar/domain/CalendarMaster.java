package com.workflow.office.calendar.domain;

import java.time.LocalDateTime;

import com.workflow.office.calendar.dto.CalendarDTO;

import lombok.AccessLevel;
import lombok.Builder;
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


	@Builder
	public CalendarMaster(String calName, String calType, Integer ownerId, Integer deptId, String useYn, String delYn, Integer regId, LocalDateTime regDt) {
		this.calName = calName;
		this.calType = calType;
		this.ownerId = ownerId;
		this.deptId = deptId;
		this.useYn = (useYn != null) ? useYn : "Y";
		this.delYn = (delYn != null) ? delYn : "N";
		this.regId = regId;
		this.regDt = LocalDateTime.now();
	}
	
	public void validateCalendarCreate() {
		if (this.calName == null || this.calName.isBlank()) {
			throw new IllegalArgumentException("calNm.required");
		}
		if (this.calType == null || this.calName.isBlank()) {
			throw new IllegalArgumentException("calTp.required");
		}
	}
	
	public void updateMaster(CalendarDTO.MasterUpdateRequest updateDto) {
		if (updateDto.getCalName() == null || updateDto.getCalName().isBlank()) {
			throw new IllegalArgumentException("calNm.required");
		}
		
		if (updateDto.getCalType() == null || updateDto.getCalType().isBlank()) {
			throw new IllegalArgumentException("calTp.required");
		}
		
		this.calName = updateDto.getCalName();
		this.calType = updateDto.getCalType();
		this.useYn = updateDto.getUseYn() != null ? updateDto.getUseYn() : this.useYn;
		this.delYn = updateDto.getDelYn() != null ? updateDto.getDelYn() : this.delYn;
		this.modId = updateDto.getModId();
		this.modDt = LocalDateTime.now();
	}
	
	public void changeDelYn(String delYn, Integer modId) {
		this.delYn = delYn;
		this.modId = modId;
		this.modDt = LocalDateTime.now();
	}
}
