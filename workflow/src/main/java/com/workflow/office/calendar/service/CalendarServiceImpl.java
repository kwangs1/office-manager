package com.workflow.office.calendar.service;

import org.springframework.stereotype.Service;

import com.workflow.office.calendar.mapper.CalendarMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

	private final CalendarMapper calendarMapper;
}
