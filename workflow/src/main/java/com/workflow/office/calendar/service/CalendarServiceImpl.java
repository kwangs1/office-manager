package com.workflow.office.calendar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workflow.office.calendar.domain.CalendarMaster;
import com.workflow.office.calendar.domain.CalendarShare;
import com.workflow.office.calendar.dto.CalendarDTO;
import com.workflow.office.calendar.mapper.CalendarMapper;
import com.workflow.office.global.response.dto.DataNotFoundException;
import com.workflow.office.org.dept.service.DeptService;
import com.workflow.office.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class CalendarServiceImpl implements CalendarService{

	private final CalendarMapper calendarMapper;
	private final UserService userService;
	private final DeptService deptService;

	@Override
	public List<CalendarDTO.Response> list() {
		List<CalendarMaster> calList = calendarMapper.list();
		if (calList == null || calList.isEmpty()) return new ArrayList<>();
		
		List<Integer> masterIds = calList.stream().map(CalendarMaster::getCalMasterId).toList();
		List<CalendarShare> allShares = calendarMapper.findShareListByMasterIds(masterIds);
		
		Map<Integer, List<CalendarDTO.ShareResponse>> shareMap = allShares.stream()
				.map(share -> {
					String name = "USER".equals(share.getTargetType())
								? userService.getUserName(share.getTargetId())
								: deptService.getDeptName(share.getTargetId());
					return new CalendarDTO.ShareResponse(share, name);
				})
				.collect(Collectors.groupingBy(CalendarDTO.ShareResponse::getCalMasterId));
		
		return calList.stream()
				.map(master -> new CalendarDTO.Response (
						master,
						shareMap.getOrDefault(master.getCalMasterId(), new ArrayList<>())
				))
				.toList();
	}

	@Override
	public CalendarDTO.Response info(Integer calendarId) {
		if (calendarId == null) {
			throw new IllegalArgumentException("calendar.not.found");
		}
		CalendarMaster info = calendarMapper.info(calendarId);
		if (info == null) {
			throw new DataNotFoundException("calendar.not.found");
		}
		List<CalendarShare> shareList = calendarMapper.findShareMasterId(calendarId);
		List<CalendarDTO.ShareResponse> shareResponseList = shareList.stream()
				.map(share -> {
					String name = "USER".equals(share.getTargetType())
								? userService.getUserName(share.getTargetId())
								: deptService.getDeptName(share.getTargetId());
					return new CalendarDTO.ShareResponse(share, name);
				})
				.toList();
		return new CalendarDTO.Response(info,shareResponseList);
	}

	@Override
	@Transactional
	public CalendarDTO.Response register(CalendarDTO.MasterCreateRequest requestDto) {
		CalendarMaster master = requestDto.toEntity();
		master.validateCalendarCreate();
		
		if (calendarMapper.register(master) <= 0) {
			throw new IllegalStateException("common.err.server_error");
		} 
		
		if (requestDto.getShareList() != null && !requestDto.getShareList().isEmpty()) {
			for (CalendarDTO.ShareCreateRequest shareDto : requestDto.getShareList()) {
				CalendarShare share = CalendarShare.from(master.getCalMasterId(), master.getRegId(), shareDto);
				calendarMapper.insertShare(share);
			}
		}

		return info(master.getCalMasterId());
	}

	@Override
	@Transactional
	public CalendarDTO.Response update(Integer calendarId, CalendarDTO.MasterUpdateRequest updateDto) {
		if (calendarId == null) {
			throw new IllegalArgumentException("calendar.not.found");
		}
		CalendarMaster currentData = calendarMapper.findById(calendarId);
		if (currentData == null) {
			throw new DataNotFoundException("calendar.not.found");
		}
		currentData.updateMaster(updateDto);
		
		if (calendarMapper.update(currentData) <= 0) {
			throw new IllegalStateException("common.err.server_error");
		}
		
		calendarMapper.deleteShareByMasterId(calendarId);
		if (updateDto.getShareList() != null && !updateDto.getShareList().isEmpty()) {
			for (CalendarDTO.ShareCreateRequest shareDto : updateDto.getShareList()) {
				CalendarShare share = CalendarShare.from(calendarId, updateDto.getModId(), shareDto);
				calendarMapper.insertShare(share);
			}
		}
		
		return info(calendarId);
	}

	@Override
	@Transactional
	public CalendarDTO.Response delYnUpdate(Integer calendarId, Integer modId) {
		if (calendarId == null) {
			throw new IllegalArgumentException("calendar.not.found");
		}
		CalendarMaster currentData = calendarMapper.findById(calendarId);
		if (currentData == null) {
			throw new DataNotFoundException("calendar.not.found");
		}
		
		currentData.changeDelYn("Y", modId);
		if (calendarMapper.update(currentData) <= 0) {
			throw new IllegalStateException("common.err.server_error");
		}
		return new CalendarDTO.Response(currentData);
	}
	
}
