package com.workflow.office.calendar.controller;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.office.calendar.service.CalendarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarApiController {

	private final MessageSource messageSource;
	private final CalendarService calendarService;
}
