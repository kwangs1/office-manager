package com.workflow.office.calendar.controller;

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

import com.workflow.office.calendar.dto.CalendarDTO;
import com.workflow.office.calendar.service.CalendarService;
import com.workflow.office.global.response.ApiResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarApiController {

	private final MessageSource messageSource;
	private final CalendarService calendarService;
	
	@GetMapping("/list")
	public ResponseEntity<ApiResult<List<CalendarDTO.Response>>> list() {
		List<CalendarDTO.Response> responseDto = calendarService.list();

		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@GetMapping("/{calendarId}")
	public ResponseEntity<ApiResult<CalendarDTO.Response>> info(@PathVariable("calendarId") Integer calendarId) {
		CalendarDTO.Response responseDto = calendarService.info(calendarId);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@PostMapping
	public ResponseEntity<ApiResult<CalendarDTO.Response>> register(@RequestBody CalendarDTO.MasterCreateRequest requestDto){
		CalendarDTO.Response responseDto = calendarService.register(requestDto);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@PatchMapping("/{calendarId}")
	public ResponseEntity<ApiResult<CalendarDTO.Response>> update(@PathVariable("calendarId") Integer calendarId, @RequestBody CalendarDTO.MasterUpdateRequest updateDto){
		CalendarDTO.Response responseDto = calendarService.update(calendarId, updateDto);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	@PatchMapping("/{calendarId}/{modId}/delYn")
	public ResponseEntity<ApiResult<CalendarDTO.Response>> delYnUpdate(@PathVariable("calendarId") Integer calendarId, @PathVariable("modId") Integer modId){
		CalendarDTO.Response responseDto = calendarService.delYnUpdate(calendarId, modId);
		
		String msg = messageSource.getMessage("common.success", null, Locale.KOREA);
		return ResponseEntity.ok(ApiResult.success(msg, responseDto));
	}
	
	
}
