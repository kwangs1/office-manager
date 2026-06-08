package com.workflow.office.calendar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workflow.office.calendar.domain.CalendarMaster;
import com.workflow.office.calendar.domain.CalendarShare;
import com.workflow.office.calendar.dto.CalendarDTO;
import com.workflow.office.calendar.mapper.CalendarMapper;
import com.workflow.office.global.response.dto.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class CalendarServiceImpl implements CalendarService{

	private final CalendarMapper calendarMapper;

	@Override
	public List<CalendarDTO.Response> list() {
		List<CalendarMaster> calList = calendarMapper.list();
		
		if (calList == null) return new ArrayList<>();
		return calList.stream()
				.map(CalendarDTO.Response::new)
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
				.map(CalendarDTO.ShareResponse::new)
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
				CalendarShare share = shareDto.toEntity(master.getCalMasterId());
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
				CalendarShare share = shareDto.toEntity(calendarId);
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
