package com.workflow.office.calendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.workflow.office.calendar.domain.CalendarMaster;
import com.workflow.office.calendar.domain.CalendarShare;

@Mapper
public interface CalendarMapper {

	List<CalendarMaster> list();
	
	CalendarMaster info(Integer calendarId);
	
	List<CalendarShare> findShareMasterId(Integer calendarId);
	
	int register(CalendarMaster master);
	
	void insertShare(CalendarShare share);
	
	CalendarMaster findById(Integer calendarId);
	
	int update(CalendarMaster master);
	
	void deleteShareByMasterId(Integer calendarId);
}
