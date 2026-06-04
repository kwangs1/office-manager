package com.workflow.office.org.dept.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/org/depts")
public class DeptPageController {

	@GetMapping("/main")
	public String deptMainPage() {
		return "org/depts/deptMgmt";
	}

	@GetMapping("/dept-tree-popup")
	public String treePopup() {
		return "org/depts/dept-tree-popup";
	}
}
