package com.workflow.office.org.position.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/org/positions")
public class PositionPageController {

	@GetMapping("/main")
	public String posMainPage() {
		return "org/positions/posMgmt";
	}
}
