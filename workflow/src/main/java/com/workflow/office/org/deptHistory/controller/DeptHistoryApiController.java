package com.workflow.office.org.deptHistory.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.office.global.response.ApiResult;
import com.workflow.office.org.deptHistory.dto.DeptHistoryDTO;
import com.workflow.office.org.deptHistory.service.DeptHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/depts")
@RequiredArgsConstructor
public class DeptHistoryApiController {

	private final DeptHistoryService deptHistoryService;
	private final MessageSource messageSource;
	
	@GetMapping("/{deptId}/histories")
	public ResponseEntity<ApiResult<List<DeptHistoryDTO.Response>>> getDeptHistories(@PathVariable("deptId") Integer deptId){
		List<DeptHistoryDTO.Response> responseList = deptHistoryService.getDeptHistories(deptId);

		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseList));
	}
}
