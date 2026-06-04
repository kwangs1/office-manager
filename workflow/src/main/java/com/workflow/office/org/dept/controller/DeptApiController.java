package com.workflow.office.org.dept.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.office.global.response.ApiResult;
import com.workflow.office.org.dept.dto.DepartmentDTO;
import com.workflow.office.org.dept.dto.OrgNode;
import com.workflow.office.org.dept.service.DeptService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/org/depts")
@RequiredArgsConstructor
public class DeptApiController {

	private final DeptService deptService;
	private final MessageSource messageSource;

	@GetMapping("/tree")
	public List<OrgNode> getOrgTree(){
		return deptService.getCompleteTree();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResult<DepartmentDTO.Response>> getDetail(@PathVariable("id") Integer id){
		DepartmentDTO.Response responseDto = deptService.selectOne(id);

		return ResponseEntity.ok(ApiResult.success(responseDto));
	}
	
	@PostMapping
	public ResponseEntity<ApiResult<DepartmentDTO.Response>> register(@RequestBody DepartmentDTO.CreateRequest requestDto) {
		DepartmentDTO.Response responseDto =  deptService.register(requestDto);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);		
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResult<DepartmentDTO.Response>> update(
			@PathVariable("id") Integer id,
			@RequestBody DepartmentDTO.UpdateRequest updateDto) {
		
		DepartmentDTO.Response responseDto = deptService.update(id, updateDto);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<ApiResult<DepartmentDTO.Response>> delete(@PathVariable("id") Integer id){
		DepartmentDTO.Response responseDto = deptService.delete(id);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
}
