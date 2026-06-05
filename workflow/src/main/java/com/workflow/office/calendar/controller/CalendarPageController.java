package com.workflow.office.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendars")
public class CalendarPageController {

	@GetMapping({"", "/"})
	public String main() {
		return "calendars/main";
	}
}
