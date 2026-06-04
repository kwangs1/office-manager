package com.workflow.office.org.deptHistory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/org/dept-histories")
public class DeptHistoryPageController {

	@GetMapping("/main")
	public String deptHistoryPage() {
		return "/org/dept-histories/getDeptHistories";
	}
}
