package com.workflow.office.calendar.service;

import java.util.List;

import com.workflow.office.calendar.dto.CalendarDTO;

public interface CalendarService {

	List<CalendarDTO.Response> list();
	
	CalendarDTO.Response info(Integer calendarId);
	
	CalendarDTO.Response register(CalendarDTO.MasterCreateRequest requestDto);
	
	CalendarDTO.Response update(Integer calendarId, CalendarDTO.MasterUpdateRequest updateDto);
	
	CalendarDTO.Response delYnUpdate(Integer calendarId, Integer modId);
	
}
