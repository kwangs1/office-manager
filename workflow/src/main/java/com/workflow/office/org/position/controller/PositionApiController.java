package com.workflow.office.org.position.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.office.global.response.ApiResult;
import com.workflow.office.org.position.dto.PositionDTO;
import com.workflow.office.org.position.service.PositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/org/positions")
@RequiredArgsConstructor
public class PositionApiController {

	private final PositionService positionService;
	private final MessageSource messageSource;
	
	@GetMapping
	public ResponseEntity<ApiResult<List<PositionDTO.Response>>> getPositions(){
		List<PositionDTO.Response> posList = positionService.list();
		return ResponseEntity.ok(ApiResult.success(posList));
	}
	
	@PostMapping
	public ResponseEntity<ApiResult<PositionDTO.Response>> register(@RequestBody PositionDTO.CreateRequest requestDto){
		PositionDTO.Response responseDto = positionService.register(requestDto);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResult<PositionDTO.Response>> update(@PathVariable("id") Integer id, @RequestBody PositionDTO.CreateRequest updateDto){
		PositionDTO.Response responseDto = positionService.update(id, updateDto);

		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResult<PositionDTO.Response>> delete(@PathVariable("id") Integer id){
		PositionDTO.Response responseDto = positionService.delete(id);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
}
