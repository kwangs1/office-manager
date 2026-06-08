package com.workflow.office.calendar.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.workflow.office.calendar.domain.CalendarMaster;
import com.workflow.office.calendar.domain.CalendarShare;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class CalendarDTO {

	@Getter
	@NoArgsConstructor
	public static class MasterCreateRequest{
		private String calName;
		private String calType;
		private Integer ownerId;
		private Integer deptId;
		private String useYn;
		private String delYn;
		private Integer regId;
		
		private List<ShareCreateRequest> shareList;
		
		public CalendarMaster toEntity() {
			return CalendarMaster.builder()
					.calName(this.calName)
					.calType(this.calType)
					.ownerId(this.ownerId)
					.deptId(this.deptId)
					.useYn(this.useYn)
					.delYn(this.delYn)
					.regId(this.regId)
					.build();
		}
	}

	@Getter
	@NoArgsConstructor
	public static class ShareCreateRequest{
		private Integer calMasterId;
		private String targetType;
		private Integer targetId;
		private String permLevel;
		private Integer regId;
		
		public CalendarShare toEntity(Integer generatedMasterId) {
			return CalendarShare.builder()
					.calMasterId(generatedMasterId)
					.targetType(this.targetType)
					.targetId(this.targetId)
					.permLevel(this.permLevel)
					.regId(this.regId)
					.build();
		}
	}
	
	@Getter
	public static class ShareResponse{
		private Integer calMasterId;
		private String targetType;
		private Integer targetId;
		private String permLevel;
		private Integer regId;
		
		public ShareResponse(CalendarShare share) {
			this.calMasterId = share.getCalMasterId();
			this.targetType = share.getTargetType();
			this.targetId = share.getTargetId();
			this.permLevel = share.getPermLevel();
			this.regId = share.getRegId();
		}
	}
	
	@Getter
	@NoArgsConstructor
	public static class MasterUpdateRequest{
		private String calName;
		private String calType;
		private String useYn;
		private String delYn;
		private Integer modId;
		
		private List<ShareCreateRequest> shareList;
	}
	
	@Getter
	public static class Response{
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
		private List<ShareResponse> shareList;
		
		public Response(CalendarMaster master) {
			this(master, null);
		}
		
		public Response(CalendarMaster master, List<ShareResponse> shareList) {
			this.calMasterId = master.getCalMasterId();
			this.calName = master.getCalName();
			this.calType = master.getCalType();
			this.ownerId = master.getOwnerId();
			this.deptId = master.getDeptId();
			this.useYn = master.getUseYn();
			this.delYn = master.getDelYn();
			this.regId = master.getRegId();
			this.regDt = master.getRegDt();
			this.modId = master.getModId();
			this.modDt = master.getModDt();
			this.shareList = shareList;
		}
	}

}
